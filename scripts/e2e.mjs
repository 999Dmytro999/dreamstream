#!/usr/bin/env node

import assert from 'node:assert/strict';

const baseUrl = process.env.E2E_BASE_URL ?? 'http://127.0.0.1:8080/api';

function uniqueEmail(prefix) {
  return `${prefix}-${Date.now()}-${Math.random().toString(16).slice(2)}@example.com`;
}

async function apiRequest(path, { method = 'GET', token, body } = {}) {
  const headers = {
    Accept: 'application/json'
  };

  if (body !== undefined) {
    headers['Content-Type'] = 'application/json';
  }

  if (token) {
    headers.Authorization = `Bearer ${token}`;
  }

  const response = await fetch(`${baseUrl}${path}`, {
    method,
    headers,
    body: body === undefined ? undefined : JSON.stringify(body)
  });

  const text = await response.text();
  const contentType = response.headers.get('content-type') ?? '';
  const payload = contentType.includes('application/json') && text.length > 0 ? JSON.parse(text) : text;

  if (!response.ok) {
    throw new Error(`${method} ${path} failed with ${response.status}: ${text}`);
  }

  return {
    status: response.status,
    payload
  };
}

async function registerUser(prefix, firstName, lastName) {
  const email = uniqueEmail(prefix);
  const { payload } = await apiRequest('/auth/register', {
    method: 'POST',
    body: {
      firstName,
      lastName,
      email,
      password: 'DreamStream123!'
    }
  });

  assert.ok(payload.accessToken, 'register response should include an access token');
  assert.ok(payload.user?.id, 'register response should include a user');

  return {
    email,
    accessToken: payload.accessToken,
    user: payload.user
  };
}

async function main() {
  await apiRequest('/health');

  const owner = await registerUser('owner', 'Owner', 'User');
  const helper = await registerUser('helper', 'Helper', 'User');

  const requestPayload = {
    title: 'Need help moving a couch',
    description: 'I need one person with a truck this Saturday afternoon.',
    category: 'OTHER',
    location: 'Chicago, IL'
  };

  const createdRequest = await apiRequest('/requests', {
    method: 'POST',
    token: owner.accessToken,
    body: requestPayload
  });

  assert.equal(createdRequest.status, 201, 'create request should return 201');
  assert.equal(createdRequest.payload.title, requestPayload.title, 'created request should echo the title');
  assert.equal(createdRequest.payload.status, 'OPEN', 'new request should start open');

  const requestId = createdRequest.payload.id;
  assert.ok(requestId, 'created request should include an id');

  const offerPayload = {
    message: 'I can help with this on Saturday.'
  };

  const createdOffer = await apiRequest(`/requests/${requestId}/offers`, {
    method: 'POST',
    token: helper.accessToken,
    body: offerPayload
  });

  assert.equal(createdOffer.status, 201, 'create offer should return 201');
  assert.equal(createdOffer.payload.helpRequestId, requestId, 'offer should belong to the created request');
  assert.equal(createdOffer.payload.message, offerPayload.message, 'offer should echo the submitted message');
  assert.equal(createdOffer.payload.status, 'PENDING', 'new offer should start pending');

  const requestDetails = await apiRequest(`/requests/${requestId}`);
  assert.equal(requestDetails.payload.id, requestId, 'request details should be fetchable by id');

  const offers = await apiRequest(`/requests/${requestId}/offers`);
  assert.ok(Array.isArray(offers.payload), 'offers endpoint should return a list');
  assert.equal(offers.payload.length, 1, 'offers endpoint should return the created offer');
  assert.equal(offers.payload[0].id, createdOffer.payload.id, 'offers endpoint should include the created offer');

  console.log(`E2E smoke test passed for request ${requestId} with owner ${owner.email} and helper ${helper.email}.`);
}

main().catch((error) => {
  console.error(error instanceof Error ? error.stack ?? error.message : String(error));
  process.exitCode = 1;
});
