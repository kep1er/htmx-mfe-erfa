/** @type {import('tailwindcss').Config} */
module.exports = {
    content: [
        "../nginx/html/**/*.html",
        "../nginx/assets/js/**/*.js",
        "../../apps/catalog-service/src/main/resources/templates/**/*.html",
        "../../apps/order-service/src/main/resources/templates/**/*.html"
    ],
    theme: {
        extend: {}
    },
    plugins: []
};
