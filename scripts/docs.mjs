#!/usr/bin/env node

import { readFileSync, existsSync } from 'node:fs';
import path from 'node:path';
import { fileURLToPath } from 'node:url';

const scriptDir = path.dirname(fileURLToPath(import.meta.url));
const repoRoot = path.resolve(scriptDir, '..');

const requiredFiles = [
  'AGENTS.md',
  'docs/architecture.md',
  'docs/migration-plan.md',
  'docs/product-requirements.md',
  'docs/adr/0001-modular-monolith.md',
  '.github/CODEOWNERS',
  '.github/dependabot.yml',
  '.github/pull_request_template.md',
];

for (const relativePath of requiredFiles) {
  const absolutePath = path.join(repoRoot, relativePath);
  if (!existsSync(absolutePath)) {
    throw new Error(`Required file missing: ${relativePath}`);
  }

  const contents = readFileSync(absolutePath, 'utf8').trim();
  if (contents.length === 0) {
    throw new Error(`Required file is empty: ${relativePath}`);
  }
}

console.log(`Validated ${requiredFiles.length} repository guidance files.`);
