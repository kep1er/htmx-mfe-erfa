CREATE TABLE IF NOT EXISTS magic_shop_items (
    id VARCHAR(64) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price NUMERIC(10,2) NOT NULL CHECK (price >= 0),
    stock INTEGER NOT NULL CHECK (stock >= 0),
    category VARCHAR(120) NOT NULL,
    rarity VARCHAR(16) NOT NULL CHECK (rarity IN ('common', 'uncommon', 'rare', 'legendary')),
    description TEXT,
    image VARCHAR(512),
    lore TEXT,
    maker VARCHAR(255),
    origin VARCHAR(255),
    weight_grams INTEGER CHECK (weight_grams >= 0),
    dimensions_length_mm INTEGER CHECK (dimensions_length_mm >= 0),
    dimensions_width_mm INTEGER CHECK (dimensions_width_mm >= 0),
    dimensions_height_mm INTEGER CHECK (dimensions_height_mm >= 0),
    released_at TIMESTAMPTZ,
    attunement_required BOOLEAN NOT NULL DEFAULT FALSE,
    charges_max INTEGER CHECK (charges_max >= 0),
    charges_recharge VARCHAR(120)
);

CREATE TABLE IF NOT EXISTS magic_shop_item_tags (
    item_id VARCHAR(64) NOT NULL REFERENCES magic_shop_items(id) ON DELETE CASCADE,
    tag VARCHAR(120) NOT NULL,
    PRIMARY KEY (item_id, tag)
);

CREATE TABLE IF NOT EXISTS magic_shop_item_effects (
    item_id VARCHAR(64) NOT NULL REFERENCES magic_shop_items(id) ON DELETE CASCADE,
    effect TEXT NOT NULL,
    PRIMARY KEY (item_id, effect)
);

CREATE TABLE IF NOT EXISTS magic_shop_item_limitations (
    item_id VARCHAR(64) NOT NULL REFERENCES magic_shop_items(id) ON DELETE CASCADE,
    limitation TEXT NOT NULL,
    PRIMARY KEY (item_id, limitation)
);

CREATE TABLE IF NOT EXISTS magic_shop_item_instructions (
    item_id VARCHAR(64) NOT NULL REFERENCES magic_shop_items(id) ON DELETE CASCADE,
    instruction TEXT NOT NULL,
    PRIMARY KEY (item_id, instruction)
);

CREATE TABLE IF NOT EXISTS magic_shop_item_warnings (
    item_id VARCHAR(64) NOT NULL REFERENCES magic_shop_items(id) ON DELETE CASCADE,
    warning TEXT NOT NULL,
    PRIMARY KEY (item_id, warning)
);

CREATE TABLE IF NOT EXISTS magic_shop_item_materials (
    item_id VARCHAR(64) NOT NULL REFERENCES magic_shop_items(id) ON DELETE CASCADE,
    material VARCHAR(255) NOT NULL,
    PRIMARY KEY (item_id, material)
);

CREATE TABLE IF NOT EXISTS magic_shop_item_related (
    item_id VARCHAR(64) NOT NULL REFERENCES magic_shop_items(id) ON DELETE CASCADE,
    related_item_id VARCHAR(64) NOT NULL,
    PRIMARY KEY (item_id, related_item_id)
);

INSERT INTO magic_shop_items (
    id,
    name,
    price,
    stock,
    category,
    rarity,
    description,
    image,
    lore,
    maker,
    origin,
    weight_grams,
    dimensions_length_mm,
    dimensions_width_mm,
    dimensions_height_mm,
    released_at,
    attunement_required,
    charges_max,
    charges_recharge
)
VALUES
    (
        'itm_001',
        'Pocket Weather Jar (Mini)',
        24.90,
        12,
        'Curios',
        'uncommon',
        'Shake gently to summon a polite thundercloud.',
        '/img/items/weather-jar-mini.jpg',
        'Bottled by the Skywrights of West Brume during the Fog Festival.',
        'Skywright Cooperative',
        'West Brume',
        220,
        70,
        70,
        110,
        '2026-01-09T10:00:00Z',
        FALSE,
        NULL,
        NULL
    ),
    (
        'itm_014',
        'Ember Thread Cloak',
        89.00,
        5,
        'Apparel',
        'rare',
        'A travel cloak that keeps drizzle warm and shoulders dry.',
        '/img/items/ember-thread-cloak.jpg',
        'Woven from ember-silk by the Seventh Loomwright Circle.',
        'Seventh Loomwright Circle',
        'Ashfall Reach',
        640,
        420,
        320,
        25,
        '2025-11-03T08:30:00Z',
        FALSE,
        NULL,
        NULL
    ),
    (
        'itm_021',
        'Whispering Teacup Set',
        39.90,
        7,
        'Hearthware',
        'common',
        'Two cups that keep tea warm and volume low.',
        '/img/items/whispering-teacups.jpg',
        'Popular among archivists who sip while cataloging.',
        'Quiet Kiln Guild',
        'Lantern Harbor',
        480,
        140,
        140,
        90,
        '2025-10-18T13:45:00Z',
        FALSE,
        NULL,
        NULL
    ),
    (
        'itm_033',
        'Moonlit Compass',
        54.50,
        9,
        'Navigation',
        'uncommon',
        'Points true under moonlight, even in fog.',
        '/img/items/moonlit-compass.jpg',
        'Favored by wardens who patrol clouded cliffs.',
        'Northline Artificers',
        'Greyshore',
        190,
        85,
        85,
        30,
        '2025-12-01T22:15:00Z',
        FALSE,
        NULL,
        NULL
    ),
    (
        'itm_042',
        'Ledger of Returning Coins',
        149.00,
        3,
        'Arcana',
        'legendary',
        'Marks borrowed coins and whispers when they return.',
        '/img/items/returning-ledger.jpg',
        'Compiled by itinerant accountants of the Third Mint.',
        'Third Mint Collective',
        'Copperway',
        910,
        240,
        180,
        35,
        '2024-09-14T07:00:00Z',
        TRUE,
        5,
        'Recharges 1 at dawn'
    ),
    (
        'itm_057',
        'Lantern of Patient Fire',
        64.75,
        11,
        'Illumination',
        'rare',
        'Burns with a calm flame that steadies trembling hands.',
        '/img/items/patient-lantern.jpg',
        'Originally gifted to apprentice healers before night rounds.',
        'Amberglass Studio',
        'Sunken Quay',
        530,
        160,
        160,
        280,
        '2025-08-26T18:05:00Z',
        FALSE,
        12,
        'Recharges fully after one quiet night'
    )
ON CONFLICT (id) DO NOTHING;

INSERT INTO magic_shop_item_tags (item_id, tag)
VALUES
    ('itm_001', 'gift'),
    ('itm_001', 'desk-friendly'),
    ('itm_014', 'travel'),
    ('itm_014', 'weatherproof'),
    ('itm_021', 'kitchen'),
    ('itm_021', 'quiet'),
    ('itm_033', 'outdoors'),
    ('itm_033', 'navigation'),
    ('itm_042', 'finance'),
    ('itm_042', 'attunement'),
    ('itm_057', 'lighting'),
    ('itm_057', 'safety')
ON CONFLICT DO NOTHING;

INSERT INTO magic_shop_item_effects (item_id, effect)
VALUES
    ('itm_001', 'Creates a tiny drizzle'),
    ('itm_001', 'Soft thunder ambience'),
    ('itm_014', 'Keeps rain from soaking shoulders'),
    ('itm_021', 'Keeps tea warm for one hour'),
    ('itm_033', 'Finds north under moonlight'),
    ('itm_042', 'Records marked coin signatures'),
    ('itm_057', 'Steady glow in high winds')
ON CONFLICT DO NOTHING;

INSERT INTO magic_shop_item_limitations (item_id, limitation)
VALUES
    ('itm_001', 'No lightning near paperwork'),
    ('itm_001', 'Sulks in dry climates'),
    ('itm_014', 'Loses warmth if submerged'),
    ('itm_033', 'Needs moonlight for true bearing'),
    ('itm_042', 'Tracks only coins marked in this ledger'),
    ('itm_057', 'Cannot be extinguished by ordinary blowing')
ON CONFLICT DO NOTHING;

INSERT INTO magic_shop_item_instructions (item_id, instruction)
VALUES
    ('itm_001', 'Tap twice'),
    ('itm_001', 'Shake 3 seconds'),
    ('itm_001', 'Set on a flat surface'),
    ('itm_014', 'Fasten clasp at collarbone'),
    ('itm_021', 'Warm cup with first pour'),
    ('itm_033', 'Expose dial to moonlight for 10 seconds'),
    ('itm_042', 'Write borrower name before handoff'),
    ('itm_057', 'Twist lower ring clockwise to light')
ON CONFLICT DO NOTHING;

INSERT INTO magic_shop_item_warnings (item_id, warning)
VALUES
    ('itm_001', 'Do not open indoors.'),
    ('itm_014', 'Keep away from open forge fire.'),
    ('itm_021', 'Cup whispers are not legal advice.'),
    ('itm_033', 'Do not rely on this item underground.'),
    ('itm_042', 'Do not attempt to bind cursed currency.'),
    ('itm_057', 'Lantern casing may be hot after long use.')
ON CONFLICT DO NOTHING;

INSERT INTO magic_shop_item_materials (item_id, material)
VALUES
    ('itm_001', 'glass'),
    ('itm_001', 'copper'),
    ('itm_001', 'storm-silk'),
    ('itm_014', 'ember-silk'),
    ('itm_014', 'bronze clasp'),
    ('itm_021', 'porcelain'),
    ('itm_021', 'silver glaze'),
    ('itm_033', 'moonsteel'),
    ('itm_033', 'oak'),
    ('itm_042', 'vellum'),
    ('itm_042', 'gold thread'),
    ('itm_057', 'amber glass'),
    ('itm_057', 'brass')
ON CONFLICT DO NOTHING;

INSERT INTO magic_shop_item_related (item_id, related_item_id)
VALUES
    ('itm_001', 'itm_014'),
    ('itm_001', 'itm_033'),
    ('itm_014', 'itm_057'),
    ('itm_033', 'itm_001'),
    ('itm_042', 'itm_033'),
    ('itm_057', 'itm_014')
ON CONFLICT DO NOTHING;
