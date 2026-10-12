# Changelog

## 0.2.0-beta.1 — Unreleased

### Added

- Support the current documented SMS delivery fields, conditional rules and decisions, and embedded signing/branding options.
- Expose 21 CFR Part 11 indicators in document responses and updated passcode-delivery settings.

### Changed

- Refresh generated resources, request/response models, and API documentation from the current published SignWell API.
- Regenerate with OpenAPI Generator 7.26.0 while preserving existing minimum runtimes.
- Standardize the default SDK User-Agent; callers can continue to configure their own value.
- Represent authenticated-user and active-user IDs as strings so legacy numeric IDs deserialize correctly. Membership, account, and contact IDs retain their UUID types.

### Dependencies

- Update compatible JUnit and Maven build/validation plugins.
- Update Jakarta Annotations to 3.0.0, verified compatible with Java 11, and complete the CodeQL/OSV scanner Action updates.

### Fixed

- Retry explicit HTTP 429 responses up to two times across raw APIs, resource helpers, and downloads. Honor `Retry-After` seconds or dates, fall back to exponential backoff, and stop waits on cancellation. Network failures and other status codes are not retried. Use `maxRateLimitRetries(0)` to disable.

### Migration

- Code that explicitly uses `UUID` for user IDs should use `String`. This does not change membership, account, or contact ID types.

## 0.1.0-beta.1

- Initial beta release of the SignWell Java SDK.
- All 28 generated API operations, including internal document and template listing endpoints.
- Async resource and convenience APIs, explicit response modes, pagination, polling, embedded workflows, typed errors, and webhook verification.
