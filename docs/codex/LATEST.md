[USER NOTES START]
- No extra user notes.
[USER NOTES END]

[CODEX HANDOFF START]
Handoff file: docs/codex/LATEST.md
Session: 2026-02-25 - simplify-handoff-to-latest
Goal:
- Switch Codex handoff artifacts to a single always-updated `docs/codex/LATEST.md`.

What changed:
- Updated [docs/CODEX_WORKING_AGREEMENT.md](C:/Users/David/IdeaProjects/htmx-mfe-erfa/docs/CODEX_WORKING_AGREEMENT.md) to use single-file handoff (`docs/codex/LATEST.md`) and updated Standard Chat Header reference.
- Created/updated [docs/codex/LATEST.md](C:/Users/David/IdeaProjects/htmx-mfe-erfa/docs/codex/LATEST.md) with required delimiters and placeholder handoff block.
- Updated [docs/codex/TEMPLATE.md](C:/Users/David/IdeaProjects/htmx-mfe-erfa/docs/codex/TEMPLATE.md) to match LATEST format (no dated filename).
- Updated [docs/SESSION_LOG.md](C:/Users/David/IdeaProjects/htmx-mfe-erfa/docs/SESSION_LOG.md) and [docs/TODO.md](C:/Users/David/IdeaProjects/htmx-mfe-erfa/docs/TODO.md).
- Removed dated artifact file [docs/codex/2026-02-25_codex-handoff-artifact-rules.md](C:/Users/David/IdeaProjects/htmx-mfe-erfa/docs/codex/2026-02-25_codex-handoff-artifact-rules.md).

Commands to run:
- `git status --short`
- `git add docs/CODEX_WORKING_AGREEMENT.md docs/SESSION_LOG.md docs/TODO.md docs/codex/TEMPLATE.md docs/codex/LATEST.md`
- `git add -A docs/codex`
- `git commit -m "chore(docs): switch codex handoff to LATEST.md"`

Verification performed:
- Commands run: `git status --short` before commit and after commit.
- Result: scoped docs-only commit created; working tree is clean.

Commit hash(es):
- `005b28b7c7b0c10e96b7281060426448c9f96f18`

Working tree status:
- Clean.

Changed files:
- [docs/CODEX_WORKING_AGREEMENT.md](C:/Users/David/IdeaProjects/htmx-mfe-erfa/docs/CODEX_WORKING_AGREEMENT.md)
- [docs/SESSION_LOG.md](C:/Users/David/IdeaProjects/htmx-mfe-erfa/docs/SESSION_LOG.md)
- [docs/TODO.md](C:/Users/David/IdeaProjects/htmx-mfe-erfa/docs/TODO.md)
- [docs/codex/LATEST.md](C:/Users/David/IdeaProjects/htmx-mfe-erfa/docs/codex/LATEST.md)
- [docs/codex/TEMPLATE.md](C:/Users/David/IdeaProjects/htmx-mfe-erfa/docs/codex/TEMPLATE.md)
- [docs/codex/2026-02-25_codex-handoff-artifact-rules.md](C:/Users/David/IdeaProjects/htmx-mfe-erfa/docs/codex/2026-02-25_codex-handoff-artifact-rules.md)

Known issues / follow-ups:
- None.

Suggested next prompt:
- Add a tiny check script to validate `docs/codex/LATEST.md` contains both required delimiters before commit.
[CODEX HANDOFF END]
