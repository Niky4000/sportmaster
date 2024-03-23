
INSERT INTO employee (fio,job_title)VALUES('Mark','storekeeper');
INSERT INTO employee (fio,job_title)VALUES('Pavel','cashier');
INSERT INTO employee (fio,job_title)VALUES('Armen','hall manager');
INSERT INTO employee (fio,job_title)VALUES('Boris','hall employee');
INSERT INTO employee (fio,job_title)VALUES('Tamara','hall employee');

INSERT INTO task (name,task_type,employee_id,status,priority, lead_time)
VALUES ('task1', 'ACCEPTANCE_OF_GOODS',1,'APPOINTED',1, 30);
INSERT INTO task (name,task_type,employee_id,status,priority, lead_time)
VALUES ('task2', 'CUSTOMER_ASSISTANCE',1,'IN_PROGRESS',2, 5);
INSERT INTO task (name,task_type,employee_id,status,priority, lead_time)
VALUES ('task3', 'INVENTORY',1,'IN_PROGRESS',3, 15);
INSERT INTO task (name,task_type,employee_id,status,priority, lead_time)
VALUES ('task-aa', 'CUSTOMER_ASSISTANCE',2,'DONE',1, 15);
INSERT INTO task (name,task_type,employee_id,status,priority, lead_time)
VALUES ('task-bb', 'CUSTOMER_ASSISTANCE',2,'DONE',2, 15);
INSERT INTO task (name,task_type,employee_id,status,priority, lead_time)
VALUES ('new-task-cc', 'INVENTORY',2,'APPOINTED',1, 60);
INSERT INTO task (name,task_type,employee_id,status,priority, lead_time)
VALUES ('taskA', 'CUSTOMER_ASSISTANCE',3,'IN_PROGRESS',4, 15);
INSERT INTO task (name,task_type,employee_id,status,priority, lead_time)
VALUES ('task-for-boris-1', 'CUSTOMER_SERVICE',4,'DONE',1, 30);
INSERT INTO task (name,task_type,employee_id,status,priority, lead_time)
VALUES ('task-for-boris-2', 'ACCEPTANCE_OF_GOODS',4,'DONE',2, 25);
INSERT INTO task (name,task_type,employee_id,status,priority, lead_time)
VALUES ('task-for-boris-3', 'CUSTOMER_ASSISTANCE',4,'APPOINTED',3, 10);
INSERT INTO task (name,task_type,employee_id,status,priority, lead_time)
VALUES ('task-for-boris-4', 'CUSTOMER_ASSISTANCE',4,'IN_PROGRESS',4, 15);
INSERT INTO task (name,task_type,employee_id,status,priority, lead_time)
VALUES ('task-for-boris-5', 'CUSTOMER_ASSISTANCE',4,'IN_PROGRESS',4, 15);
INSERT INTO task (name,task_type,employee_id,status,priority, lead_time)
VALUES ('taskA1', 'CUSTOMER_ASSISTANCE',5,'IN_PROGRESS',4, 15);