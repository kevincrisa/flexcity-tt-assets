-- Insert Assets
INSERT INTO asset (code, name, activation_cost, volume)
VALUES ('A1', 'Asset 1', 100.0, 50) ON CONFLICT (code) DO NOTHING;
INSERT INTO asset (code, name, activation_cost, volume)
VALUES ('A2', 'Asset 2', 80.0, 40) ON CONFLICT (code) DO NOTHING;
INSERT INTO asset (code, name, activation_cost, volume)
VALUES ('A3', 'Asset 3', 120.0, 60) ON CONFLICT (code) DO NOTHING;
INSERT INTO asset (code, name, activation_cost, volume)
VALUES ('A4', 'Asset 4', 90.0, 35) ON CONFLICT (code) DO NOTHING;
INSERT INTO asset (code, name, activation_cost, volume)
VALUES ('A5', 'Asset 5', 150.0, 70) ON CONFLICT (code) DO NOTHING;
INSERT INTO asset (code, name, activation_cost, volume)
VALUES ('A6', 'Asset 6', 60.0, 25) ON CONFLICT (code) DO NOTHING;
INSERT INTO asset (code, name, activation_cost, volume)
VALUES ('A7', 'Asset 7', 110.0, 55) ON CONFLICT (code) DO NOTHING;
INSERT INTO asset (code, name, activation_cost, volume)
VALUES ('A8', 'Asset 8', 95.0, 45) ON CONFLICT (code) DO NOTHING;
INSERT INTO asset (code, name, activation_cost, volume)
VALUES ('A9', 'Asset 9', 75.0, 30) ON CONFLICT (code) DO NOTHING;
INSERT INTO asset (code, name, activation_cost, volume)
VALUES ('A10', 'Asset 10', 130.0, 65) ON CONFLICT (code) DO NOTHING;
INSERT INTO asset (code, name, activation_cost, volume)
VALUES ('A11', 'Asset 11', 85.0, 20) ON CONFLICT (code) DO NOTHING;
INSERT INTO asset (code, name, activation_cost, volume)
VALUES ('A12', 'Asset 12', 140.0, 60) ON CONFLICT (code) DO NOTHING;
INSERT INTO asset (code, name, activation_cost, volume)
VALUES ('A13', 'Asset 13', 55.0, 15) ON CONFLICT (code) DO NOTHING;
INSERT INTO asset (code, name, activation_cost, volume)
VALUES ('A14', 'Asset 14', 160.0, 75) ON CONFLICT (code) DO NOTHING;
INSERT INTO asset (code, name, activation_cost, volume)
VALUES ('A15', 'Asset 15', 70.0, 35) ON CONFLICT (code) DO NOTHING;

-- Insert Asset Availabilities
INSERT INTO asset_availability (asset_code, date)
VALUES ('A1', '2026-03-10') ON CONFLICT DO NOTHING;
INSERT INTO asset_availability (asset_code, date)
VALUES ('A1', '2026-03-11') ON CONFLICT DO NOTHING;
INSERT INTO asset_availability (asset_code, date)
VALUES ('A2', '2026-03-10') ON CONFLICT DO NOTHING;
INSERT INTO asset_availability (asset_code, date)
VALUES ('A3', '2026-03-12') ON CONFLICT DO NOTHING;
INSERT INTO asset_availability (asset_code, date)
VALUES ('A4', '2026-03-11') ON CONFLICT DO NOTHING;
INSERT INTO asset_availability (asset_code, date)
VALUES ('A5', '2026-03-10') ON CONFLICT DO NOTHING;
INSERT INTO asset_availability (asset_code, date)
VALUES ('A5', '2026-03-12') ON CONFLICT DO NOTHING;
INSERT INTO asset_availability (asset_code, date)
VALUES ('A6', '2026-03-10') ON CONFLICT DO NOTHING;
INSERT INTO asset_availability (asset_code, date)
VALUES ('A7', '2026-03-11') ON CONFLICT DO NOTHING;
INSERT INTO asset_availability (asset_code, date)
VALUES ('A8', '2026-03-12') ON CONFLICT DO NOTHING;
INSERT INTO asset_availability (asset_code, date)
VALUES ('A9', '2026-03-10') ON CONFLICT DO NOTHING;
INSERT INTO asset_availability (asset_code, date)
VALUES ('A10', '2026-03-11') ON CONFLICT DO NOTHING;
INSERT INTO asset_availability (asset_code, date)
VALUES ('A10', '2026-03-12') ON CONFLICT DO NOTHING;
INSERT INTO asset_availability (asset_code, date)
VALUES ('A11', '2026-03-10') ON CONFLICT DO NOTHING;
INSERT INTO asset_availability (asset_code, date)
VALUES ('A12', '2026-03-12') ON CONFLICT DO NOTHING;
INSERT INTO asset_availability (asset_code, date)
VALUES ('A13', '2026-03-11') ON CONFLICT DO NOTHING;
INSERT INTO asset_availability (asset_code, date)
VALUES ('A14', '2026-03-10') ON CONFLICT DO NOTHING;
INSERT INTO asset_availability (asset_code, date)
VALUES ('A15', '2026-03-12') ON CONFLICT DO NOTHING;