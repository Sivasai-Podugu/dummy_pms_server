CREATE TABLE deductions (
                            `id` INT AUTO_INCREMENT PRIMARY KEY,
                            `employee_id` INT,
                            `provident_fund` DECIMAL(10, 2),
                            `tax` DECIMAL(10, 2),
                            FOREIGN KEY (`employee_id`) REFERENCES employees(`id`)
                                ON DELETE CASCADE
                                ON UPDATE CASCADE
);