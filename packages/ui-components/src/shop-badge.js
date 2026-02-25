import {LitElement, css, html} from "https://cdn.jsdelivr.net/npm/lit@3.3.1/+esm";

class ShopBadge extends LitElement {
    static properties = {
        label: {type: String}
    };

    static styles = css`
        :host {
            display: inline-block;
        }

        .badge {
            border: 1px solid #0f172a;
            border-radius: 9999px;
            padding: 0.25rem 0.75rem;
            background: #e2e8f0;
            color: #0f172a;
            font-size: 0.75rem;
            font-weight: 600;
            letter-spacing: 0.03em;
            text-transform: uppercase;
        }
    `;

    constructor() {
        super();
        this.label = "Demo";
    }

    render() {
        return html`<span class="badge">${this.label}</span>`;
    }
}

customElements.define("shop-badge", ShopBadge);
