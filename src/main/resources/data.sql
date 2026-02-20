DELETE FROM transactions;DELETE FROM wallets;DELETE FROM users;
INSERT INTO users (id, name, email, created_at) VALUES
('11111111-1111-1111-1111-111111111111', 'User1', 'user1@test.com', NOW()),
('22222222-2222-2222-2222-222222222222', 'User2', 'user2@test.com', NOW()),
('33333333-3333-3333-3333-333333333333', 'User3', 'user3@test.com', NOW()),
('44444444-4444-4444-4444-444444444444', 'User4', 'user4@test.com', NOW()),
('55555555-5555-5555-5555-555555555555', 'User5', 'user5@test.com', NOW());

INSERT INTO wallets (id, user_id, balance, created_at) VALUES
('aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1',
 '11111111-1111-1111-1111-111111111111',
 100, NOW()),

('aaaaaaa2-aaaa-aaaa-aaaa-aaaaaaaaaaa2',
 '22222222-2222-2222-2222-222222222222',
 100, NOW()),

('aaaaaaa3-aaaa-aaaa-aaaa-aaaaaaaaaaa3',
 '33333333-3333-3333-3333-333333333333',
 100, NOW()),

('aaaaaaa4-aaaa-aaaa-aaaa-aaaaaaaaaaa4',
 '44444444-4444-4444-4444-444444444444',
 100, NOW()),

('aaaaaaa5-aaaa-aaaa-aaaa-aaaaaaaaaaa5',
 '55555555-5555-5555-5555-555555555555',
 100, NOW());