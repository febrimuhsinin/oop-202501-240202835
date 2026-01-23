-- Seed data for Agri-POS database

INSERT INTO products (code, name, price, stock) VALUES
('BNH-001', 'Benih Padi', 25000.00, 100),
('PPT-001', 'Pupuk Organik', 50000.00, 50),
('ALT-001', 'Cangkul', 75000.00, 20)
ON CONFLICT (code) DO NOTHING;