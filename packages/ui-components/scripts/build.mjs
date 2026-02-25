import {cp, mkdir, readFile, writeFile} from "node:fs/promises";
import path from "node:path";
import {fileURLToPath} from "node:url";

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);
const packageRoot = path.resolve(__dirname, "..");

const source = path.join(packageRoot, "src", "shop-badge.js");
const distDir = path.join(packageRoot, "dist");
const distTarget = path.join(distDir, "shop-badge.js");
const nginxAssetsDir = path.resolve(packageRoot, "..", "..", "web", "nginx", "assets", "ui-components");
const nginxTarget = path.join(nginxAssetsDir, "shop-badge.js");

await mkdir(distDir, {recursive: true});
await cp(source, distTarget, {force: true});

await mkdir(nginxAssetsDir, {recursive: true});
await cp(source, nginxTarget, {force: true});

const banner = "/* Built by packages/ui-components/scripts/build.mjs */\n";
const content = await readFile(distTarget, "utf8");
await writeFile(distTarget, banner + content, "utf8");
await writeFile(nginxTarget, banner + content, "utf8");

console.log("Built shop-badge component:");
console.log(`- ${distTarget}`);
console.log(`- ${nginxTarget}`);
