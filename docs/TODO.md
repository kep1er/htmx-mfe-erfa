# TODO

## Recently Done
- [x] Polish shell/catalog UI layout: aligned top bar, peer-based pill radios, responsive list/detail catalog view, and cohesive row-card action spacing.
- [x] Fix top-bar pill highlight bug so only the selected rarity/category radio pill is shown as active.

## Next (Active)
- [ ] Add a one-command helper for host-run Spring dev mode (starts both services with `dev` profile).
- [ ] Add a small cross-platform helper script to start both Spring apps with `dev` profile in separate terminals.
- [ ] Add a dedicated Playwright test file for catalog filtering permutations (keep smoke test small).

## Soon
- [ ] Add CI workflow to run `npm run e2e:all` (or equivalent split steps) on PRs.
- [ ] Add optional catalog batch summary endpoint for cart optimization (defer until needed).

## Later
- [ ] Persist cart/order data (if desired).
- [ ] Add reusable shared Lit components for product card/cart badge.
- [ ] Add integration tests for fragment endpoints.
- [ ] Improve styling and design consistency.
- [ ] Add CI checks (build/test/lint).

## Blocked / Questions
- [ ] Confirm whether to keep Option A per-line cart summary requests or move to a batched summary fetch when cart size grows.
