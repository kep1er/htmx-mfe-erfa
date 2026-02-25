[CODEX HANDOFF START]
Session: 2026-02-25 - codex-handoff-artifact-rules
Goal:
- Introduce a repository handoff artifact format so each session summary is saved and easy to copy.

What changed:
- Updated `docs/CODEX_WORKING_AGREEMENT.md` to require handoff files at `docs/codex/YYYY-MM-DD_<slug>.md`.
- Added explicit handoff delimiter format: `[CODEX HANDOFF START]` and `[CODEX HANDOFF END]`.
- Added required handoff sections and a clean working tree rule.
- Added required final chat header format with `[USER NOTES START] ... [USER NOTES END]` before the handoff block.
- Added template file: `docs/codex/TEMPLATE.md`.
- Updated `docs/SESSION_LOG.md` and `docs/TODO.md` to reflect this session.

Commands to run:
- `git status --short`
- `git add docs/CODEX_WORKING_AGREEMENT.md docs/SESSION_LOG.md docs/TODO.md docs/codex/TEMPLATE.md`
- `git commit -m "chore(docs): add codex handoff artifact template and rules"`

Verification performed:
- Commands run: `git status --short` before commit.
- Result: only scoped task files were present; commit succeeded; post-commit tree is clean.

Commit hash(es):
- `5e530b6f98ac5340c9779c0db3f999a0bc1d4e93`

Working tree status:
- Clean (`git status --short` has no output).

Changed files:
- [docs/CODEX_WORKING_AGREEMENT.md](C:/Users/David/IdeaProjects/htmx-mfe-erfa/docs/CODEX_WORKING_AGREEMENT.md)
- [docs/codex/TEMPLATE.md](C:/Users/David/IdeaProjects/htmx-mfe-erfa/docs/codex/TEMPLATE.md)
- [docs/SESSION_LOG.md](C:/Users/David/IdeaProjects/htmx-mfe-erfa/docs/SESSION_LOG.md)
- [docs/TODO.md](C:/Users/David/IdeaProjects/htmx-mfe-erfa/docs/TODO.md)

Known issues / follow-ups:
- None. No unrelated changes were left uncommitted.

Suggested next prompt:
- Create and use the first dated handoff artifact file `docs/codex/YYYY-MM-DD_<slug>.md` from `docs/codex/TEMPLATE.md` in the next implementation task.
[CODEX HANDOFF END]
