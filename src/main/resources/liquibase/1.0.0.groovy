databaseChangeLog {

    changeSet(id: '2017-02-04-01', author: 'Vladimir Zburzhynski <zburzhynski@gmail.com>') {
        comment("Create jsender scheme")
        sql(stripComments: true, splitStatements: false, endDelimiter: ';') {
            "create schema jsender authorization jsender"
        }
    }

    changeSet(id: '2017-02-04-02', author: 'Nikita Shevtsov <shevtsou@gmail.com>') {
        createTable(schemaName: 'jsender', tableName: 'person', tablespace: 'jsender_data', remarks: 'Person') {
            column(name: 'id', type: 'VARCHAR(128)', remarks: 'The unique identifier of person') {
                constraints(nullable: false)
            }
            column(name: 'name', type: 'VARCHAR(50)', remarks: 'The person name') {
                constraints(nullable: false)
            }
            column(name: 'surname', type: 'VARCHAR(50)', remarks: 'The person surname') {
                constraints(nullable: false)
            }
            column(name: 'patronymic', type: 'VARCHAR(50)', remarks: 'The person patronymic') {
                constraints(nullable: false)
            }
            column(name: 'birthday', type: 'DATE', remarks: 'The person birthday') {
                constraints(nullable: false)
            }
            column(name: 'gender', type: 'CHAR(1)', remarks: 'The person sex') {
                constraints(nullable: false)
            }
        }
        addPrimaryKey(schemaName: 'jsender', tableName: 'person', tablespace: 'jsender_index',
                columnNames: 'id', constraintName: 'PK_person')
    }

    changeSet(id: '2017-02-04-03', author: 'Nikita Shevtsov <shevtsou@gmail.com>') {
        createTable(schemaName: 'jsender', tableName: 'contact_info', tablespace: 'jsender_data', remarks: 'Contact info') {
            column(name: 'id', type: 'VARCHAR(128)', remarks: 'The unique identifier for contact info') {
                constraints(nullable: false)
            }
        }
        addPrimaryKey(schemaName: 'jsender', tableName: 'contact_info', tablespace: 'jsender_index',
                columnNames: 'id', constraintName: 'PK_contact_info')
    }

    changeSet(id: '2017-02-04-04', author: 'Nikita Shevtsov <shevtsou@gmail.com>') {
        createTable(schemaName: 'jsender', tableName: 'contact_info_email', tablespace: 'jsender_data', remarks: 'Contact info email') {
            column(name: 'id', type: 'VARCHAR(128)', remarks: 'The unique identifier for email') {
                constraints(nullable: false)
            }
            column(name: 'contact_info_id', type: 'VARCHAR(128)', remarks: 'The reference to contact_info table') {
                constraints(nullable: false)
            }
            column(name: 'address', type: 'VARCHAR(50)', remarks: 'Address of email') {
                constraints(nullable: false)
            }
            column(name: 'description', type: 'VARCHAR(1000)', remarks: 'Email description')
            column(name: 'sort_order', type: 'INTEGER', remarks: 'The order of email') {
                constraints(nullable: false)
            }
        }
        addPrimaryKey(schemaName: 'jsender', tableName: 'contact_info_email', tablespace: 'jsender_index',
                columnNames: 'id', constraintName: 'PK_contact_info_email')
    }

    changeSet(id: '2017-02-04-05', author: 'Nikita Shevtsov <shevtsou@gmail.com>') {
        comment("Added foreign constraint FK_contact_info_email_2_contact_info")
        addForeignKeyConstraint(constraintName: 'FK_contact_info_email_2_contact_info',
                baseTableSchemaName: 'jsender', baseTableName: 'contact_info_email' , baseColumnNames: 'contact_info_id',
                referencedTableSchemaName: 'jsender', referencedTableName: 'contact_info', referencedColumnNames: 'id')
    }

    changeSet(id: '2017-02-04-06', author: 'Nikita Shevtsov <shevtsou@gmail.com>') {
        createTable(schemaName: 'jsender', tableName: 'contact_info_phone', tablespace: 'jsender_data', remarks: 'Contact info phone') {
            column(name: 'id', type: 'VARCHAR(128)', remarks: 'The unique identifier for phone') {
                constraints(nullable: false)
            }
            column(name: 'contact_info_id', type: 'VARCHAR(128)', remarks: 'The reference to contact_info table') {
                constraints(nullable: false)
            }
            column(name: 'country_code', type: 'VARCHAR(10)', remarks: 'Country code')
            column(name: 'city_code', type: 'VARCHAR(10)', remarks: 'City code')
            column(name: 'phone_number', type: 'VARCHAR(20)', remarks: 'Phone number') {
                constraints(nullable: false)
            }
            column(name: 'phone_number_type', type: 'VARCHAR(10)', remarks: 'Phone number type') {
                constraints(nullable: false)
            }
            column(name: 'mobile_operator', type: 'VARCHAR(128)', remarks: 'Mobile operator')
            column(name: 'description', type: 'VARCHAR(1000)', remarks: 'The phone number description')
            column(name: 'sort_order', type: 'INTEGER', remarks: 'The order of phone number') {
                constraints(nullable: false)
            }
        }
        addPrimaryKey(schemaName: 'jsender', tableName: 'contact_info_phone', tablespace: 'jsender_index',
                columnNames: 'id', constraintName: 'PK_contact_info_phone')
    }

    changeSet(id: '2017-02-04-07', author: 'Nikita Shevtsov <shevtsou@gmail.com>') {
        comment("Added foreign constraint FK_contact_info_phone_2_contact_info")
        addForeignKeyConstraint(constraintName: 'FK_contact_info_phone_2_contact_info',
                baseTableSchemaName: 'jsender', baseTableName: 'contact_info_phone', baseColumnNames: 'contact_info_id',
                referencedTableSchemaName: 'jsender', referencedTableName: 'contact_info', referencedColumnNames: 'id')
    }

    changeSet(id: '2017-02-04-08', author: 'Nikita Shevtsov <shevtsou@gmail.com>') {
        createTable(schemaName: 'jsender', tableName: 'client', tablespace: 'jsender_data', remarks: 'Client') {
            column(name: 'id', type: 'VARCHAR(128)', remarks: 'The unique identifier for client') {
                constraints(nullable: false)
            }
            column(name: 'person_id', type: 'VARCHAR(128)', remarks: 'The reference to person table') {
                constraints(nullable: false)
            }
            column(name: 'contact_info_id', type: 'VARCHAR(128)', remarks: 'The reference to contact_info table') {
                constraints(nullable: false)
            }
        }
        addPrimaryKey(schemaName: 'jsender', tableName: 'client', tablespace: 'jsender_index',
                columnNames: 'id', constraintName: 'PK_client')
    }

    changeSet(id: '2017-02-04-9', author: 'Nikita Shevtsov <shevtsou@gmail.com>') {
        comment("Added foreign constraint FK_client_2_contact_info")
        addForeignKeyConstraint(constraintName: 'FK_client_2_contact_info',
                baseTableSchemaName: 'jsender', baseTableName: 'client', baseColumnNames: 'contact_info_id',
                referencedTableSchemaName: 'jsender', referencedTableName: 'contact_info', referencedColumnNames: 'id')
    }

    changeSet(id: '2017-02-04-10', author: 'Nikita Shevtsov <shevtsou@gmail.com>') {
        comment("Added foreign constraint FK_client_2_person")
        addForeignKeyConstraint(constraintName: 'FK_client_2_person',
                baseTableSchemaName: 'jsender', baseTableName: 'client', baseColumnNames: 'person_id',
                referencedTableSchemaName: 'jsender', referencedTableName: 'person', referencedColumnNames: 'id')
    }

    changeSet(id: '2017-02-04-11', author: 'Nikita Shevtsov <shevtsou@gmail.com>') {
        createTable(schemaName: 'jsender', tableName: 'setting', tablespace: 'jsender_data', remarks: 'Application settings') {
            column(name: 'id', type: 'VARCHAR(128)', remarks: 'The unique identifier of setting') {
                constraints(nullable: false)
            }
            column(name: 'category', type: 'VARCHAR(30)', remarks: 'The setting category') {
                constraints(nullable: false)
            }
            column(name: 'name', type: 'VARCHAR(50)', remarks: 'The setting name') {
                constraints(nullable: false)
            }
            column(name: 'value', type: 'VARCHAR(250)', remarks: 'The setting value') {
                constraints(nullable: false)
            }
            column(name: 'type', type: 'VARCHAR(15)', remarks: 'The setting value type') {
                constraints(nullable: false)
            }
            column(name: 'description', type: 'VARCHAR(500)', remarks: 'The setting description') {
                constraints(nullable: false)
            }
        }
        addPrimaryKey(schemaName: 'jsender', tableName: 'setting', tablespace: 'jsender_index',
                columnNames: 'id', constraintName: 'PK_setting')
    }

    changeSet(id: '2017-02-04-12', author: 'Nikita Shevtsov <shevtsou@gmail.com>') {
        comment("Create unique constraint on setting table")
        sql(stripComments: true, splitStatements: false, endDelimiter: ';') {
            """
            CREATE UNIQUE INDEX UK_setting_name ON setting (name);
            """
        }
    }

    changeSet(id: '2017-02-04-13', author: 'Nikita Shevtsov <shevtsou@gmail.com>') {
        insert(schemaName: 'jsender', tableName: 'setting') {
            column(name: 'id', value: 'dd23cd60-76fc-4dae-87cd-8b7ce777747d')
            column(name: 'category', value: 'VIEW')
            column(name: 'name', value: 'clients_per_page')
            column(name: 'value', value: '20')
            column(name: 'type', value: 'INTEGER')
            column(name: 'description', value: 'Количество клиентов на странице')
        }
    }

    changeSet(id: '2017-02-04-14', author: 'Nikita Shevtsov <shevtsou@gmail.com>') {
        insert(schemaName: 'jsender', tableName: 'setting') {
            column(name: 'id', value: 'a039ad9b-4a0a-4622-a549-ce355c45cea1')
            column(name: 'category', value: 'EMAIL_SENDING')
            column(name: 'name', value: 'mail_smtp_host')
            column(name: 'value', value: 'smtp.gmail.com')
            column(name: 'type', value: 'STRING')
            column(name: 'description', value: 'Сервер SMTP')
        }
        insert(schemaName: 'jsender', tableName: 'setting') {
            column(name: 'id', value: '5c2c2cbe-c3d3-4161-8daf-c13c6da2b849')
            column(name: 'category', value: 'EMAIL_SENDING')
            column(name: 'name', value: 'mail_smtp_port')
            column(name: 'value', value: '587')
            column(name: 'type', value: 'STRING')
            column(name: 'description', value: 'Порт SMTP')
        }
        insert(schemaName: 'jsender', tableName: 'setting') {
            column(name: 'id', value: '2e995ca7-3bce-4705-85ac-822c6fbd317e')
            column(name: 'category', value: 'EMAIL_SENDING')
            column(name: 'name', value: 'mail_user_name')
            column(name: 'value', value: 'username')
            column(name: 'type', value: 'STRING')
            column(name: 'description', value: 'Имя пользователя')
        }
        insert(schemaName: 'jsender', tableName: 'setting') {
            column(name: 'id', value: '0112434e-3885-4d13-9d6e-14706195fba3')
            column(name: 'category', value: 'EMAIL_SENDING')
            column(name: 'name', value: 'mail_password')
            column(name: 'value', value: 'password')
            column(name: 'type', value: 'PASSWORD')
            column(name: 'description', value: 'Пароль')
        }
    }

    changeSet(id: '2017-02-11-01', author: 'Nikita Shevtsov <shevtsou@gmail.com>') {
        insert(schemaName: 'jsender', tableName: 'setting') {
            column(name: 'id', value: '75e3f5a0-4395-4f7d-831d-874aa38b4bf8')
            column(name: 'category', value: 'COMMON')
            column(name: 'name', value: 'default_country_code')
            column(name: 'value', value: '+375')
            column(name: 'type', value: 'STRING')
            column(name: 'description', value: 'Код страны по умолчанию')
        }
    }

    changeSet(id: '2017-02-12-01', author: 'Nikita Shevtsov <shevtsou@gmail.com>') {
        insert(schemaName: 'jsender', tableName: 'setting') {
            column(name: 'id', value: '3806dd31-ba70-4e53-8577-9f2105cfb1cb')
            column(name: 'category', value: 'SMS_SENDING')
            column(name: 'name', value: 'sms_user_name')
            column(name: 'value', value: 'username')
            column(name: 'type', value: 'STRING')
            column(name: 'description', value: 'Имя пользователя')
        }
        insert(schemaName: 'jsender', tableName: 'setting') {
            column(name: 'id', value: 'df859fcb-ed4f-4073-83e7-1901f6489d60')
            column(name: 'category', value: 'SMS_SENDING')
            column(name: 'name', value: 'sms_password')
            column(name: 'value', value: 'password')
            column(name: 'type', value: 'PASSWORD')
            column(name: 'description', value: 'Пароль')
        }
    }

    changeSet(id: '2017-02-15-01', author: 'Nikita Shevtsov <shevtsou@gmail.com>') {
        insert(schemaName: 'jsender', tableName: 'setting') {
            column(name: 'id', value: '445157fe-0ea5-4702-94e3-eb9cd4948024')
            column(name: 'category', value: 'REQUISITE')
            column(name: 'name', value: 'organization_name')
            column(name: 'value', value: 'Название организации')
            column(name: 'type', value: 'STRING')
            column(name: 'description', value: 'Название организации')
        }
        insert(schemaName: 'jsender', tableName: 'setting') {
            column(name: 'id', value: 'c9a11401-2795-4cac-a172-10ee4ebd5734')
            column(name: 'category', value: 'REQUISITE')
            column(name: 'name', value: 'organization_address')
            column(name: 'value', value: 'Адрес организации')
            column(name: 'type', value: 'STRING')
            column(name: 'description', value: 'Адрес организации')
        }
        insert(schemaName: 'jsender', tableName: 'setting') {
            column(name: 'id', value: '3226f6cb-314e-4c7a-91a2-5993469de0b0')
            column(name: 'category', value: 'REQUISITE')
            column(name: 'name', value: 'organization_mobile_phone_number')
            column(name: 'value', value: 'Мобильный телефон организации')
            column(name: 'type', value: 'STRING')
            column(name: 'description', value: 'Мобильный телефон организации')
        }
    }

    changeSet(id: '2017-02-16-01', author: 'Nikita Shevtsov <shevtsou@gmail.com>') {
        createTable(schemaName: 'jsender', tableName: 'sent_message', tablespace: 'jsender_data', remarks: 'Sent message') {
            column(name: 'id', type: 'VARCHAR(128)', remarks: 'The unique identifier for message') {
                constraints(nullable: false)
            }
            column(name: 'sent_date', type: 'TIMESTAMP', remarks: 'The sent date') {
                constraints(nullable: false)
            }
            column(name: 'sending_type', type: 'VARCHAR(5)', remarks: 'Message sending_type') {
                constraints(nullable: false)
            }
            column(name: 'client_id', type: 'VARCHAR(128)', remarks: 'The reference to client table') {
                constraints(nullable: false)
            }
            column(name: 'client_source', type: 'VARCHAR(10)', remarks: 'The client source') {
                constraints(nullable: false)
            }
            column(name: 'contact_info', type: 'VARCHAR(50)', remarks: 'The message contact info') {
                constraints(nullable: false)
            }
            column(name: 'subject', type: 'VARCHAR(250)', remarks: 'Message subject')
            column(name: 'text', type: 'VARCHAR(2000)', remarks: 'Message sent text') {
                constraints(nullable: false)
            }
            column(name: 'status', type: 'VARCHAR(20)', remarks: 'Message status') {
                constraints(nullable: false)
            }
        }
        addPrimaryKey(schemaName: 'jsender', tableName: 'sent_message', tablespace: 'jsender_index',
                columnNames: 'id', constraintName: 'PK_sent_message')
    }

    changeSet(id: '2017-02-19-01', author: 'Nikita Shevtsov <shevtsou@gmail.com>') {
        insert(schemaName: 'jsender', tableName: 'setting') {
            column(name: 'id', value: '571b6b24-90ae-4f00-8a96-b94e97ccee8f')
            column(name: 'category', value: 'VIEW')
            column(name: 'name', value: 'sent_messages_per_page')
            column(name: 'value', value: '20')
            column(name: 'type', value: 'INTEGER')
            column(name: 'description', value: 'Количество отправленных сообщений на странице')
        }
    }

    changeSet(id: '2017-02-21-01', author: 'Nikita Shevtsov <shevtsou@gmail.com>') {
        createTable(schemaName: 'jsender', tableName: 'message_template', tablespace: 'jsender_data', remarks: 'Message template') {
            column(name: 'id', type: 'VARCHAR(128)', remarks: 'The unique identifier for message template') {
                constraints(nullable: false)
            }
            column(name: 'subject', type: 'VARCHAR(250)', remarks: 'Message template subject') {
                constraints(nullable: false)
            }
            column(name: 'text', type: 'VARCHAR(2000)', remarks: 'Message template text') {
                constraints(nullable: false)
            }
        }
        addPrimaryKey(schemaName: 'jsender', tableName: 'message_template', tablespace: 'jsender_index',
                columnNames: 'id', constraintName: 'PK_message_template')
    }

    changeSet(id: '2017-02-21-02', author: 'Nikita Shevtsov <shevtsou@gmail.com>') {
        insert(schemaName: 'jsender', tableName: 'setting') {
            column(name: 'id', value: 'f8ea9abe-b5b3-4147-a936-4811d588063c')
            column(name: 'category', value: 'VIEW')
            column(name: 'name', value: 'message_templates_per_page')
            column(name: 'value', value: '20')
            column(name: 'type', value: 'INTEGER')
            column(name: 'description', value: 'Количество шаблонов сообщений на странице')
        }
    }

    changeSet(id: '2017-02-25-01', author: 'Nikita Shevtsov <shevtsou@gmail.com>') {
        insert(schemaName: 'jsender', tableName: 'setting') {
            column(name: 'id', value: 'a5e0e9f9-98b0-4cfa-a8ac-8adbe4c6afff')
            column(name: 'category', value: 'JDENT')
            column(name: 'name', value: 'jdent_integration_enabled')
            column(name: 'value', value: 'false')
            column(name: 'type', value: 'BOOLEAN')
            column(name: 'description', value: 'Интеграция с jDent')
        }

        insert(schemaName: 'jsender', tableName: 'setting') {
            column(name: 'id', value: '6bfc2406-e1cf-4992-91ce-989bcc5f4ff0')
            column(name: 'category', value: 'JDENT')
            column(name: 'name', value: 'jdent_url')
            column(name: 'value', value: 'http://localhost:8080/jdent/')
            column(name: 'type', value: 'STRING')
            column(name: 'description', value: 'Ссылка на jDent')
        }
    }

    changeSet(id: '2017-02-12-02', author: 'Nikita Shevtsov <shevtsou@gmail.com>') {
        insert(schemaName: 'jsender', tableName: 'setting') {
            column(name: 'id', value: '6b3c3a1a-8332-445d-8e83-2491c5cef5d6')
            column(name: 'category', value: 'VIEW')
            column(name: 'name', value: 'sending_recipients_per_page')
            column(name: 'value', value: '20')
            column(name: 'type', value: 'INTEGER')
            column(name: 'description', value: 'Количество получателей на странице рассылки')
        }
    }

    changeSet(id: '2017-02-28-01', author: 'Nikita Shevtsov <shevtsou@gmail.com>') {
        insert(schemaName: 'jsender', tableName: 'setting') {
            column(name: 'id', value: '1033a628-fe58-4382-9929-0d98d67e8fa8')
            column(name: 'category', value: 'VIEW')
            column(name: 'name', value: 'search_recipients_per_page')
            column(name: 'value', value: '20')
            column(name: 'type', value: 'INTEGER')
            column(name: 'description', value: 'Количество получателей на странице поиска')
        }
    }

    changeSet(id: '2017-03-05-01', author: 'Nikita Shevtsov <shevtsou@gmail.com>') {
        createTable(schemaName: 'jsender', tableName: 'sending_service', tablespace: 'jsender_data', remarks: 'Sending service') {
            column(name: 'id', type: 'VARCHAR(128)', remarks: 'The unique identifier of sending service') {
                constraints(nullable: false)
            }
            column(name: 'name', type: 'VARCHAR(200)', remarks: 'The sending service name') {
                constraints(nullable: false)
            }
            column(name: 'sending_type', type: 'VARCHAR(5)', remarks: 'Service sending type') {
                constraints(nullable: false)
            }
            column(name: 'description', type: 'VARCHAR(1000)', remarks: 'Sending service description') {
                constraints(nullable: false)
            }
        }
        addPrimaryKey(schemaName: 'jsender', tableName: 'sending_service', tablespace: 'jsender_index',
                columnNames: 'id', constraintName: 'PK_sending_service')
    }

    changeSet(id: '2017-03-05-02', author: 'Nikita Shevtsov <shevtsou@gmail.com>') {
        createTable(schemaName: 'jsender', tableName: 'sending_service_param', tablespace: 'jsender_data', remarks: 'Sending service param') {
            column(name: 'id', type: 'VARCHAR(128)', remarks: 'The unique identifier of sending service param') {
                constraints(nullable: false)
            }
            column(name: 'sending_service_id', type: 'VARCHAR(128)', remarks: 'The reference to sending_service table') {
                constraints(nullable: false)
            }
            column(name: 'name', type: 'VARCHAR(50)', remarks: 'The sending service param name') {
                constraints(nullable: false)
            }
            column(name: 'type', type: 'VARCHAR(10)', remarks: 'Sending param type') {
                constraints(nullable: false)
            }
            column(name: 'value', type: 'VARCHAR(250)', remarks: 'Sending param value')
            column(name: 'description', type: 'VARCHAR(1000)', remarks: 'Sending service description') {
                constraints(nullable: false)
            }
        }
        addPrimaryKey(schemaName: 'jsender', tableName: 'sending_service_param', tablespace: 'jsender_index',
                columnNames: 'id', constraintName: 'PK_sending_service_param')
    }

    changeSet(id: '2017-03-05-03', author: 'Nikita Shevtsov <shevtsou@gmail.com>') {
        comment("Added foreign constraint FK_sending_service_param_2_sending_service")
        addForeignKeyConstraint(constraintName: 'FK_sending_service_param_2_sending_service',
                baseTableSchemaName: 'jsender', baseTableName: 'sending_service_param' , baseColumnNames: 'sending_service_id',
                referencedTableSchemaName: 'jsender', referencedTableName: 'sending_service', referencedColumnNames: 'id')
    }

    changeSet(id: '2017-03-05-04', author: 'Nikita Shevtsov <shevtsou@gmail.com>') {
        createTable(schemaName: 'jsender', tableName: 'employee_sending_service', tablespace: 'jsender_data', remarks: 'Employee sending service') {
            column(name: 'id', type: 'VARCHAR(128)', remarks: 'The unique identifier of sending service') {
                constraints(nullable: false)
            }
            column(name: 'sending_service_id', type: 'VARCHAR(128)', remarks: 'The reference to sending_service table') {
                constraints(nullable: false)
            }
        }
        addPrimaryKey(schemaName: 'jsender', tableName: 'employee_sending_service', tablespace: 'jsender_index',
                columnNames: 'id', constraintName: 'PK_employee_sending_service')
    }

    changeSet(id: '2017-03-05-05', author: 'Nikita Shevtsov <shevtsou@gmail.com>') {
        comment("Added foreign constraint FK_employee_sending_service_2_sending_service")
        addForeignKeyConstraint(constraintName: 'FK_employee_sending_service_2_sending_service',
                baseTableSchemaName: 'jsender', baseTableName: 'employee_sending_service' , baseColumnNames: 'sending_service_id',
                referencedTableSchemaName: 'jsender', referencedTableName: 'sending_service', referencedColumnNames: 'id')
    }

    changeSet(id: '2017-03-05-06', author: 'Nikita Shevtsov <shevtsou@gmail.com>') {
        createTable(schemaName: 'jsender', tableName: 'employee_sending_service_param', tablespace: 'jsender_data', remarks: 'Employee sending service param') {
            column(name: 'id', type: 'VARCHAR(128)', remarks: 'The unique identifier of employee sending service param') {
                constraints(nullable: false)
            }
            column(name: 'employee_sending_service_id', type: 'VARCHAR(128)', remarks: 'The reference to employee_sending_service table') {
                constraints(nullable: false)
            }
            column(name: 'sending_service_param_id', type: 'VARCHAR(128)', remarks: 'The reference to sending_service_param table') {
                constraints(nullable: false)
            }
            column(name: 'value', type: 'VARCHAR(250)', remarks: 'Employee service sending param value') {
                constraints(nullable: false)
            }
        }
        addPrimaryKey(schemaName: 'jsender', tableName: 'employee_sending_service_param', tablespace: 'jsender_index',
                columnNames: 'id', constraintName: 'PK_employee_sending_service_param')
    }

    changeSet(id: '2017-03-05-07', author: 'Nikita Shevtsov <shevtsou@gmail.com>') {
        comment("Added foreign constraint FK_employee_sending_service_param_2_employee_sending_service")
        addForeignKeyConstraint(constraintName: 'FK_employee_sending_service_param_2_employee_sending_service',
                baseTableSchemaName: 'jsender', baseTableName: 'employee_sending_service_param' , baseColumnNames: 'employee_sending_service_id',
                referencedTableSchemaName: 'jsender', referencedTableName: 'employee_sending_service', referencedColumnNames: 'id')
    }

    changeSet(id: '2017-03-05-08', author: 'Nikita Shevtsov <shevtsou@gmail.com>') {
        comment("Added foreign constraint FK_employee_sending_service_param_2_sending_service_param")
        addForeignKeyConstraint(constraintName: 'FK_employee_sending_service_param_2_sending_service_param',
                baseTableSchemaName: 'jsender', baseTableName: 'employee_sending_service_param' , baseColumnNames: 'sending_service_param_id',
                referencedTableSchemaName: 'jsender', referencedTableName: 'sending_service_param', referencedColumnNames: 'id')
    }

    changeSet(id: '2017-03-07-01', author: 'Nikita Shevtsov <shevtsou@gmail.com>') {
        insert(schemaName: 'jsender', tableName: 'setting') {
            column(name: 'id', value: '8a6e5c45-d915-493d-ac29-2c5abd0e5977')
            column(name: 'category', value: 'VIEW')
            column(name: 'name', value: 'sending_accounts_per_page')
            column(name: 'value', value: '20')
            column(name: 'type', value: 'INTEGER')
            column(name: 'description', value: 'Количество аккаунтов на странице')
        }
    }

    changeSet(id: '2017-03-08-01', author: 'Nikita Shevtsov <shevtsou@gmail.com>') {
        comment("Insert gmail parameters")
        sql(stripComments: true, splitStatements: false, endDelimiter: ';') {
            """
            insert into jsender.sending_service values('06967298-6360-4d8f-af5f-a20d58dd4414', 'gmail.com', 'EMAIL', 'Сервис для массовой емейл рассылки');
            insert into jsender.sending_service_param values('797c97ad-0621-401b-9795-8ef5568a9a48', '06967298-6360-4d8f-af5f-a20d58dd4414', 'Сервер SMTP', 'STRING', 'smtp.gmail.com', 'Сервер SMTP');
            insert into jsender.sending_service_param values('173b22e7-1789-4e96-b03f-73ca8fe97489', '06967298-6360-4d8f-af5f-a20d58dd4414', 'Порт SMTP', 'STRING', '587', 'Сервер SMTP');
            insert into jsender.sending_service_param values('e9ef8298-8638-4976-bb56-1274d6b9fa73', '06967298-6360-4d8f-af5f-a20d58dd4414', 'Имя пользователя', 'STRING', null, 'Имя пользователя');
            insert into jsender.sending_service_param values('6e42b739-ff7f-4843-bb81-05d2c009a165', '06967298-6360-4d8f-af5f-a20d58dd4414', 'Пароль', 'PASSWORD', null, 'Пароль');
            """
        }
    }

}