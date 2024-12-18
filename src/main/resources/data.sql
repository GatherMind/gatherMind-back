INSERT INTO member (member_id, nickname, password, created_at, email, profile_image, is_delete, role,OAUTH_PROVIDER) VALUES
('member1', 'Alpha', 'Password123', CURRENT_TIMESTAMP- INTERVAL '1' DAY, 'user1@example.com', '/api/files/default-profile',0,1,'LOCAL'),
('member2', 'Beta', 'Password456', CURRENT_TIMESTAMP- INTERVAL '1' DAY, 'user2@example.com', '/api/files/default-profile',0,2,'LOCAL'),
('member3', 'Gamma', 'Password789', CURRENT_TIMESTAMP- INTERVAL '1' DAY, 'user3@example.com', '/api/files/default-profile',0,1,'LOCAL'),
('member4', 'Delta', 'Password101', CURRENT_TIMESTAMP- INTERVAL '1' DAY, 'user4@example.com', '/api/files/default-profile',0,2,'LOCAL'),
('member5', 'Epsilon', 'Password202', CURRENT_TIMESTAMP- INTERVAL '1' DAY, 'user5@example.com', '/api/files/default-profile',0,2,'LOCAL'),
('member6', 'Zeta', 'Password303', CURRENT_TIMESTAMP- INTERVAL '1' DAY, 'user6@example.com', '/api/files/default-profile',0,2,'LOCAL'),
('member7', 'Eta', 'Password404', CURRENT_TIMESTAMP- INTERVAL '1' DAY, 'user7@example.com', '/api/files/default-profile',0,2,'LOCAL'),
('member8', 'Theta', 'Password505', CURRENT_TIMESTAMP- INTERVAL '1' DAY, 'user8@example.com', '/api/files/default-profile',0,1,'LOCAL'),
('member9', 'Iota', 'Password606', CURRENT_TIMESTAMP- INTERVAL '1' DAY, 'user9@example.com', '/api/files/default-profile',0,2,'LOCAL'),
('member10', 'Kappa', 'Password707', CURRENT_TIMESTAMP- INTERVAL '1' DAY, 'user10@example.com', '/api/files/default-profile',0,1,'LOCAL'),
('member11', 'Lambda', 'Password808', CURRENT_TIMESTAMP- INTERVAL '1' DAY, 'user11@example.com', '/api/files/default-profile',0,2,'LOCAL'),
('member12', 'Mu', 'Password909', CURRENT_TIMESTAMP- INTERVAL '1' DAY, 'user12@example.com', '/api/files/default-profile',0,2,'LOCAL'),
('member13', 'Nu', 'Password010', CURRENT_TIMESTAMP- INTERVAL '1' DAY, 'user13@example.com', '/api/files/default-profile',0,2,'LOCAL'),
('member14', 'Xi', 'Password111', CURRENT_TIMESTAMP- INTERVAL '1' DAY, 'user14@example.com', '/api/files/default-profile',0,2,'LOCAL'),
('member15', 'Omicron', 'Password222', CURRENT_TIMESTAMP- INTERVAL '1' DAY, 'user15@example.com', '/api/files/default-profile',0,2,'LOCAL');


INSERT INTO study (study_id, title, description, status, created_at, category) VALUES
(1, 'Introduction to Web Development', 'Learn the basics of building web applications', 1, CURRENT_TIMESTAMP, 3), -- FULL_STACK
(2, 'Data Science Essentials', 'An overview of essential data science tools and techniques', 1, CURRENT_TIMESTAMP, 4), -- DATA_SCIENCE
(3, 'Advanced Python Programming', 'Master Python for complex software development', 2, CURRENT_TIMESTAMP, 3), -- FULL_STACK
(4, 'Project Management Skills', 'Learn effective project management methodologies', 1, CURRENT_TIMESTAMP, 14), -- PROJECT_MANAGEMENT
(5, 'Digital Marketing Strategies', 'Comprehensive strategies for online marketing', 1, CURRENT_TIMESTAMP, 17), -- SOFT_SKILLS
(6, 'AI and Machine Learning', 'Explore AI principles and machine learning models', 2, CURRENT_TIMESTAMP, 6), -- AI_ML
(7, 'UI/UX Design Fundamentals', 'Design user-friendly and engaging interfaces', 1, CURRENT_TIMESTAMP, 10), -- UI_UX_DESIGN
(8, 'Cloud Computing Basics', 'Understand the foundation of cloud infrastructure', 2, CURRENT_TIMESTAMP, 12), -- CLOUD_COMPUTING
(9, 'Cybersecurity Principles', 'Learn how to protect systems against cyber threats', 1, CURRENT_TIMESTAMP, 16), -- CYBERSECURITY
(10, 'Mobile App Development', 'Build feature-rich mobile applications', 1, CURRENT_TIMESTAMP, 5), -- MOBILE_APP
(11, 'Blockchain Technology', 'Explore the fundamentals of blockchain', 2, CURRENT_TIMESTAMP, 13), -- BLOCKCHAIN
(12, 'Game Development Workshop', 'Develop games using popular game engines', 2, CURRENT_TIMESTAMP, 8), -- GAME_DEVELOPMENT
(13, 'Creative Writing for Beginners', 'Learn to write compelling stories and content', 1, CURRENT_TIMESTAMP, 17), -- SOFT_SKILLS
(14, 'Entrepreneurship 101', 'Build a strong foundation for starting your business', 2, CURRENT_TIMESTAMP, 18), -- ENTREPRENEURSHIP
(15, 'Public Speaking Mastery', 'Develop confidence in presenting to audiences', 2, CURRENT_TIMESTAMP, 17), -- SOFT_SKILLS
(16, 'Networking Basics', 'Learn how computer networks operate and communicate', 1, CURRENT_TIMESTAMP, 15), -- NETWORKING
(17, 'DevOps Fundamentals', 'Learn DevOps principles and CI/CD practices', 1, CURRENT_TIMESTAMP, 7), -- DEVOPS
(18, 'Database Design', 'Master database normalization and schema design', 2, CURRENT_TIMESTAMP, 11), -- DATABASE
(19, 'Advanced Cybersecurity Techniques', 'Protect systems with advanced security measures', 2, CURRENT_TIMESTAMP, 16), -- CYBERSECURITY
(20, 'Front-End Frameworks', 'Learn modern front-end frameworks like React and Angular', 1, CURRENT_TIMESTAMP, 1); -- FRONT_END


INSERT INTO schedule (schedule_id, title, description, date_time, location, created_at, study_id, member_id) VALUES
(1, 'Session 1', 'Introduction to Basics', CURRENT_TIMESTAMP + INTERVAL '1' DAY, 'Room A', CURRENT_TIMESTAMP, 1, 'member1'),
(2, 'Session 2', 'Setting Up Tools', CURRENT_TIMESTAMP + INTERVAL '2' DAY, 'Room B', CURRENT_TIMESTAMP, 1, 'member1'),
(3, 'Session 3', 'Hands-on Coding', CURRENT_TIMESTAMP + INTERVAL '3' DAY, 'Room C', CURRENT_TIMESTAMP, 1, 'member1'),
(4, 'Session 4', 'Advanced Topics', CURRENT_TIMESTAMP + INTERVAL '4' DAY, 'Room D', CURRENT_TIMESTAMP, 1, 'member1'),
(5, 'Session 5', 'Q&A', CURRENT_TIMESTAMP + INTERVAL '5' DAY, 'Room E', CURRENT_TIMESTAMP, 1, 'member1'),
(6, 'Session 6', 'Final Project', CURRENT_TIMESTAMP + INTERVAL '6' DAY, 'Room F', CURRENT_TIMESTAMP, 1, 'member1'),
(7, 'Session 7', 'Data Analysis Techniques', CURRENT_TIMESTAMP + INTERVAL '7' DAY, 'Room G', CURRENT_TIMESTAMP, 2, 'member1'),
(8, 'Session 8', 'Visualization Tools', CURRENT_TIMESTAMP + INTERVAL '8' DAY, 'Room H', CURRENT_TIMESTAMP, 2, 'member1'),
(9, 'Session 9', 'Machine Learning Models', CURRENT_TIMESTAMP + INTERVAL '9' DAY, 'Room I', CURRENT_TIMESTAMP, 2, 'member1'),
(10, 'Session 10', 'Deep Learning', CURRENT_TIMESTAMP + INTERVAL '10' DAY, 'Room J', CURRENT_TIMESTAMP, 3, 'member1'),
(11, 'Session 11', 'Networking Basics', CURRENT_TIMESTAMP + INTERVAL '11' DAY, 'Room K', CURRENT_TIMESTAMP, 3, 'member1'),
(12, 'Session 12', 'Industry Use Cases', CURRENT_TIMESTAMP + INTERVAL '12' DAY, 'Room L', CURRENT_TIMESTAMP, 3, 'member1'),
(13, 'Session 13', 'Capstone Project', CURRENT_TIMESTAMP + INTERVAL '13' DAY, 'Room M', CURRENT_TIMESTAMP, 3, 'member1'),
(14, 'Session 14', 'Feedback and Wrap-up', CURRENT_TIMESTAMP + INTERVAL '14' DAY, 'Room N', CURRENT_TIMESTAMP, 1, 'member1'),
(15, 'Session 15', 'Graduation Ceremony', CURRENT_TIMESTAMP + INTERVAL '15' DAY, 'Room O', CURRENT_TIMESTAMP, 1, 'member1');


INSERT INTO study_member (study_member_id, role, status, progress, joined_date, member_id, study_id) VALUES
(1, 1, 2, 'Progress 1', CURRENT_TIMESTAMP, 'member1', 1),
(2, 1, 1, 'Progress 2', CURRENT_TIMESTAMP, 'member2', 1),
(3, 2, 2, 'Progress 3', CURRENT_TIMESTAMP, 'member3', 1),
(4, 1, 2, 'Progress 4', CURRENT_TIMESTAMP, 'member4', 1),
(5, 2, 1, 'Progress 5', CURRENT_TIMESTAMP, 'member5', 1),
(6, 1, 2, 'Progress 6', CURRENT_TIMESTAMP, 'member6', 1),
(7, 2, 1, 'Progress 7', CURRENT_TIMESTAMP, 'member7', 1),
(8, 2, 2, 'Progress 8', CURRENT_TIMESTAMP, 'member8', 1),
(9, 1, 2, 'Progress 9', CURRENT_TIMESTAMP, 'member9', 1),
(10, 2, 1, 'Progress 10', CURRENT_TIMESTAMP, 'member10', 1),
(11, 1, 2, 'Progress 11', CURRENT_TIMESTAMP, 'member11', 1),
(12, 2, 1, 'Progress 12', CURRENT_TIMESTAMP, 'member12', 2),
(13, 1, 2, 'Progress 13', CURRENT_TIMESTAMP, 'member13', 3),
(14, 2, 1, 'Progress 14', CURRENT_TIMESTAMP, 'member14', 4),
(15, 1, 2, 'Progress 15', CURRENT_TIMESTAMP, 'member15', 5),
(16, 1, 2, 'Progress 15', CURRENT_TIMESTAMP, 'member1', 2);

INSERT INTO question (question_id, content, title, option, created_at, study_member_id) VALUES
(1, 'Explain the purpose of HTML in web development.', 'Understanding HTML', 1, CURRENT_TIMESTAMP + INTERVAL '1' DAY, 1),
(2, 'Describe the concept of database normalization and its importance.', 'Database Normalization', 2, CURRENT_TIMESTAMP + INTERVAL '2' DAY, 2),
(3, 'What are the different types of loops in Python? Provide examples.', 'Python Loops', 1, CURRENT_TIMESTAMP + INTERVAL '3' DAY, 3),
(4, 'What are the core principles of Agile methodology?', 'Agile Methodology Basics', 2, CURRENT_TIMESTAMP + INTERVAL '4' DAY, 4),
(5, 'Discuss the main features of SEO and how they impact web traffic.', 'SEO Fundamentals', 1, CURRENT_TIMESTAMP + INTERVAL '5' DAY, 5),
(6, 'Define the term "Machine Learning" and give a practical example.', 'Introduction to Machine Learning', 2, CURRENT_TIMESTAMP + INTERVAL '6' DAY, 6),
(7, 'Explain the significance of cybersecurity in todays digital world.', 'Cybersecurity Importance', 1, CURRENT_TIMESTAMP + INTERVAL '7' DAY, 7),
(8, 'What are the best practices for creating user-friendly interfaces?', 'UI/UX Best Practices', 1, CURRENT_TIMESTAMP + INTERVAL '8' DAY, 8),
(9, 'How does cloud computing work? Describe its advantages.', 'Cloud Computing Overview', 2, CURRENT_TIMESTAMP + INTERVAL '9' DAY, 9),
(10, 'What is the role of JavaScript in dynamic web applications?', 'JavaScript for Web Development', 1, CURRENT_TIMESTAMP + INTERVAL '10' DAY, 10),
(11, 'Explain the concept of blockchain and its real-world applications.', 'Blockchain Basics', 1, CURRENT_TIMESTAMP + INTERVAL '11' DAY, 11),
(12, 'Describe the process of data visualization and its tools.', 'Data Visualization Techniques', 1, CURRENT_TIMESTAMP + INTERVAL '12' DAY, 12),
(13, 'What is the significance of mobile-first design in web development?', 'Mobile-First Design Principles', 1, CURRENT_TIMESTAMP + INTERVAL '13' DAY, 13),
(14, 'What are the steps involved in building a successful digital marketing campaign?', 'Digital Marketing Strategies', 2, CURRENT_TIMESTAMP + INTERVAL '14' DAY, 14),
(15, 'How can public speaking skills be improved for professional settings?', 'Public Speaking Tips', 1, CURRENT_TIMESTAMP + INTERVAL '15' DAY, 15);

INSERT INTO answer (answer_id, content, created_at, question_id, member_id) VALUES
(1, 'answer 1', CURRENT_TIMESTAMP + INTERVAL '1' DAY, 1, 'member1');
