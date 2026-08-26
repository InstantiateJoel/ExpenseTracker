-- Category Inserts

-- Main categories
-- Categories, that will have Sub Categories
INSERT INTO app.category
    (categoryid, "name", parent_id)
VALUES (gen_random_uuid(), 'pet', null) ON CONFLICT DO NOTHING; --sub cat

INSERT INTO app.category
    (categoryid, "name", parent_id)
VALUES (gen_random_uuid(), 'car', null) ON CONFLICT DO NOTHING; -- sub cat

INSERT INTO app.category
    (categoryid, "name", parent_id)
VALUES (gen_random_uuid(), 'housing', null) ON CONFLICT DO NOTHING; -- sub cat

INSERT INTO app.category
    (categoryid, "name", parent_id)
VALUES (gen_random_uuid(), 'service_subscription', null) ON CONFLICT DO NOTHING; --sub cat

INSERT INTO app.category
    (categoryid, "name", parent_id)
VALUES (gen_random_uuid(), 'transportation', null) ON CONFLICT DO NOTHING;

INSERT INTO app.category
    (categoryid, "name", parent_id)
VALUES (gen_random_uuid(), 'sport', null) ON CONFLICT DO NOTHING;

INSERT INTO app.category
    (categoryid, "name", parent_id)
VALUES (gen_random_uuid(), 'holidays', null) ON CONFLICT DO NOTHING;

INSERT INTO app.category
    (categoryid, "name", parent_id)
VALUES (gen_random_uuid(), 'school', null) ON CONFLICT DO NOTHING;

INSERT INTO app.category
    (categoryid, "name", parent_id)
VALUES (gen_random_uuid(), 'health', null) ON CONFLICT DO NOTHING;

-- Categories, that won't have sub categories
INSERT INTO app.category
    (categoryid, "name", parent_id)
VALUES (gen_random_uuid(), 'groceries', null) ON CONFLICT DO NOTHING; --none

INSERT INTO app.category
    (categoryid, "name", parent_id)
VALUES (gen_random_uuid(), 'dining_out', null) ON CONFLICT DO NOTHING; --none

INSERT INTO app.category
    (categoryid, "name", parent_id)
VALUES (gen_random_uuid(), 'hairdresser', null) ON CONFLICT DO NOTHING;

INSERT INTO app.category
    (categoryid, "name", parent_id)
VALUES (gen_random_uuid(), 'clothing', null) ON CONFLICT DO NOTHING;

INSERT INTO app.category
    (categoryid, "name", parent_id)
VALUES (gen_random_uuid(), 'outings', null) ON CONFLICT DO NOTHING;


-- sub categories
-- pet
INSERT INTO app.category
    (categoryid, "name", parent_id)
VALUES (gen_random_uuid(), 'pet_food', (select categoryid from app.category where name = 'pet')) ON CONFLICT DO NOTHING;

INSERT INTO app.category
    (categoryid, "name", parent_id)
VALUES (gen_random_uuid(), 'veterinary_visits', (select categoryid from app.category where name = 'pet')) ON CONFLICT DO NOTHING;

INSERT INTO app.category
    (categoryid, "name", parent_id)
VALUES (gen_random_uuid(), 'pet_meds', (select categoryid from app.category where name = 'pet')) ON CONFLICT DO NOTHING;

INSERT INTO app.category
    (categoryid, "name", parent_id)
VALUES (gen_random_uuid(), 'pet_toys', (select categoryid from app.category where name = 'pet')) ON CONFLICT DO NOTHING;

-- car
INSERT INTO app.category
    (categoryid, "name", parent_id)
VALUES (gen_random_uuid(), 'car_insurance', (select categoryid from app.category where name = 'car')) ON CONFLICT DO NOTHING;

INSERT INTO app.category
    (categoryid, "name", parent_id)
VALUES (gen_random_uuid(), 'gas', (select categoryid from app.category where name = 'car')) ON CONFLICT DO NOTHING;

INSERT INTO app.category
    (categoryid, "name", parent_id)
VALUES (gen_random_uuid(), 'repairs', (select categoryid from app.category where name = 'car')) ON CONFLICT DO NOTHING;

INSERT INTO app.category
    (categoryid, "name", parent_id)
VALUES (gen_random_uuid(), 'maintenance', (select categoryid from app.category where name = 'car')) ON CONFLICT DO NOTHING;

INSERT INTO app.category
    (categoryid, "name", parent_id)
VALUES (gen_random_uuid(), 'road_tax', (select categoryid from app.category where name = 'car')) ON CONFLICT DO NOTHING;

-- housing
INSERT INTO app.category
    (categoryid, "name", parent_id)
VALUES (gen_random_uuid(), 'rent', (select categoryid from app.category where name = 'housing')) ON CONFLICT DO NOTHING;

INSERT INTO app.category
    (categoryid, "name", parent_id)
VALUES (gen_random_uuid(), 'utilities', (select categoryid from app.category where name = 'housing')) ON CONFLICT DO NOTHING;

INSERT INTO app.category
    (categoryid, "name", parent_id)
VALUES (gen_random_uuid(), 'home_maintenance', (select categoryid from app.category where name = 'housing')) ON CONFLICT DO NOTHING;

INSERT INTO app.category
    (categoryid, "name", parent_id)
VALUES (gen_random_uuid(), 'home_supplies', (select categoryid from app.category where name = 'housing')) ON CONFLICT DO NOTHING;

-- service_subscription
INSERT INTO app.category
    (categoryid, "name", parent_id)
VALUES (gen_random_uuid(), 'mobile_phone', (select categoryid from app.category where name = 'service_subscription')) ON CONFLICT DO NOTHING;

INSERT INTO app.category
    (categoryid, "name", parent_id)
VALUES (gen_random_uuid(), 'internet', (select categoryid from app.category where name = 'service_subscription')) ON CONFLICT DO NOTHING;

INSERT INTO app.category
    (categoryid, "name", parent_id)
VALUES (gen_random_uuid(), 'tv', (select categoryid from app.category where name = 'service_subscription')) ON CONFLICT DO NOTHING;

INSERT INTO app.category
    (categoryid, "name", parent_id)
VALUES (gen_random_uuid(), 'streaming', (select categoryid from app.category where name = 'service_subscription')) ON CONFLICT DO NOTHING;

-- transportation
INSERT INTO app.category
    (categoryid, "name", parent_id)
VALUES (gen_random_uuid(), 'public_transportation',
        (select categoryid from app.category where name = 'transportation')) ON CONFLICT DO NOTHING;

INSERT INTO app.category
    (categoryid, "name", parent_id)
VALUES (gen_random_uuid(), 'taxi_rides', (select categoryid from app.category where name = 'transportation')) ON CONFLICT DO NOTHING;

INSERT INTO app.category
    (categoryid, "name", parent_id)
VALUES (gen_random_uuid(), 'bicycle', (select categoryid from app.category where name = 'transportation')) ON CONFLICT DO NOTHING;

-- sport
INSERT INTO app.category
    (categoryid, "name", parent_id)
VALUES (gen_random_uuid(), 'hiking', (select categoryid from app.category where name = 'sport')) ON CONFLICT DO NOTHING;

INSERT INTO app.category
    (categoryid, "name", parent_id)
VALUES (gen_random_uuid(), 'bowling', (select categoryid from app.category where name = 'sport')) ON CONFLICT DO NOTHING;

INSERT INTO app.category
    (categoryid, "name", parent_id)
VALUES (gen_random_uuid(), 'skiing', (select categoryid from app.category where name = 'sport')) ON CONFLICT DO NOTHING;

INSERT INTO app.category
    (categoryid, "name", parent_id)
VALUES (gen_random_uuid(), 'gym', (select categoryid from app.category where name = 'sport')) ON CONFLICT DO NOTHING;

INSERT INTO app.category
    (categoryid, "name", parent_id)
VALUES (gen_random_uuid(), 'diving', (select categoryid from app.category where name = 'sport')) ON CONFLICT DO NOTHING;


INSERT INTO app.category
    (categoryid, "name", parent_id)
VALUES (gen_random_uuid(), 'gymnastics_club', (select categoryid from app.category where name = 'sport')) ON CONFLICT DO NOTHING;

-- holidays
INSERT INTO app.category
    (categoryid, "name", parent_id)
VALUES (gen_random_uuid(), 'flight', (select categoryid from app.category where name = 'holidays')) ON CONFLICT DO NOTHING;

INSERT INTO app.category
    (categoryid, "name", parent_id)
VALUES (gen_random_uuid(), 'hotel', (select categoryid from app.category where name = 'holidays')) ON CONFLICT DO NOTHING;

INSERT INTO app.category
    (categoryid, "name", parent_id)
VALUES (gen_random_uuid(), 'other', (select categoryid from app.category where name = 'holidays')) ON CONFLICT DO NOTHING;

-- school
INSERT INTO app.category
    (categoryid, "name", parent_id)
VALUES (gen_random_uuid(), 'school_fees', (select categoryid from app.category where name = 'school')) ON CONFLICT DO NOTHING;

INSERT INTO app.category
    (categoryid, "name", parent_id)
VALUES (gen_random_uuid(), 'supplies', (select categoryid from app.category where name = 'school')) ON CONFLICT DO NOTHING;

INSERT INTO app.category
    (categoryid, "name", parent_id)
VALUES (gen_random_uuid(), 'software', (select categoryid from app.category where name = 'school')) ON CONFLICT DO NOTHING;

-- health
INSERT INTO app.category
    (categoryid, "name", parent_id)
VALUES (gen_random_uuid(), 'health_insurance', (select categoryid from app.category where name = 'health')) ON CONFLICT DO NOTHING;

INSERT INTO app.category
    (categoryid, "name", parent_id)
VALUES (gen_random_uuid(), 'meds', (select categoryid from app.category where name = 'health')) ON CONFLICT DO NOTHING;

INSERT INTO app.category
    (categoryid, "name", parent_id)
VALUES (gen_random_uuid(), 'doctor_visits', (select categoryid from app.category where name = 'health')) ON CONFLICT DO NOTHING;