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

    changeSet(id: '2017-02-12-02', author: 'Nikita Shevtsov <shevtsou@gmail.com>') {
        insert(schemaName: 'jsender', tableName: 'setting') {
            column(name: 'id', value: '6b3c3a1a-8332-445d-8e83-2491c5cef5d6')
            column(name: 'category', value: 'VIEW')
            column(name: 'name', value: 'recipients_per_page')
            column(name: 'value', value: '20')
            column(name: 'type', value: 'INTEGER')
            column(name: 'description', value: 'Количество получателей на странице')
        }
    }

    changeSet(id: '2017-02-15-01', author: 'Nikita Shevtsov <shevtsou@gmail.com>') {
        insert(schemaName: 'jsender', tableName: 'setting') {
            column(name: 'id', value: '445157fe-0ea5-4702-94e3-eb9cd4948024')
            column(name: 'category', value: 'REQUISITE')
            column(name: 'name', value: 'organization_name')
            column(name: 'value', value: '')
            column(name: 'type', value: 'STRING')
            column(name: 'description', value: 'Название')
        }
        insert(schemaName: 'jsender', tableName: 'setting') {
            column(name: 'id', value: 'c9a11401-2795-4cac-a172-10ee4ebd5734')
            column(name: 'category', value: 'REQUISITE')
            column(name: 'name', value: 'organization_address')
            column(name: 'value', value: '')
            column(name: 'type', value: 'STRING')
            column(name: 'description', value: 'Адрес')
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
            column(name: 'contact_info', type: 'VARCHAR(50)', remarks: 'The message contact info') {
                constraints(nullable: false)
            }
            column(name: 'subject', type: 'VARCHAR(200)', remarks: 'Message subject')
            column(name: 'text', type: 'VARCHAR(1000)', remarks: 'Message sent text') {
                constraints(nullable: false)
            }
            column(name: 'status', type: 'VARCHAR(20)', remarks: 'Message status') {
                constraints(nullable: false)
            }
        }
        addPrimaryKey(schemaName: 'jsender', tableName: 'sent_message', tablespace: 'jsender_index',
                columnNames: 'id', constraintName: 'PK_sent_message')
    }

    changeSet(id: '2017-02-16-02', author: 'Nikita Shevtsov <shevtsou@gmail.com>') {
        comment("Added foreign constraint FK_sent_message_2_client")
        addForeignKeyConstraint(constraintName: 'FK_sent_message_2_client',
                baseTableSchemaName: 'jsender', baseTableName: 'sent_message' , baseColumnNames: 'client_id',
                referencedTableSchemaName: 'jsender', referencedTableName: 'client', referencedColumnNames: 'id')
    }

}