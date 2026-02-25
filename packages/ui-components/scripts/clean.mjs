import {rm} from "node:fs/promises";
import path from "node:path";
import {fileURLToPath} from "node:url";

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);
const packageRoot = path.resolve(__dirname, "..");
const distDir = path.join(packageRoot, "dist");
const nginxTarget = path.resolve(packageRoot, "..", "..", "web", "nginx", "assets", "ui-components", "shop-badge.js");

await rm(distDir, {recursive: true, force: true});
await rm(nginxTarget, {force: true});

console.log("Cleaned ui-components build outputs.");
