databaseChangeLog {

    changeSet(id: '2017-03-11-01', author: 'Vladimir Zburzhynski <zburzhynski@gmail.com>') {
        comment("Create jsender scheme")
        sql(stripComments: true, splitStatements: false, endDelimiter: ';') {
            "create schema jsender authorization jsender"
        }
    }

    changeSet(id: '2017-03-11-02', author: 'Nikita Shevtsov <shevtsou@gmail.com>') {
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

    changeSet(id: '2017-03-11-03', author: 'Nikita Shevtsov <shevtsou@gmail.com>') {
        comment("Create unique constraint on setting table")
        sql(stripComments: true, splitStatements: false, endDelimiter: ';') {
            """
            CREATE UNIQUE INDEX UK_setting_name ON setting (name);
            """
        }
    }

    changeSet(id: '2017-03-11-04', author: 'Nikita Shevtsov <shevtsou@gmail.com>') {
        comment("Insert common settings")
        insert(schemaName: 'jsender', tableName: 'setting') {
            column(name: 'id', value: '75e3f5a0-4395-4f7d-831d-874aa38b4bf8')
            column(name: 'category', value: 'COMMON')
            column(name: 'name', value: 'default_country_code')
            column(name: 'value', value: '+375')
            column(name: 'type', value: 'STRING')
            column(name: 'description', value: 'Код страны по умолчанию')
        }
        insert(schemaName: 'jsender', tableName: 'setting') {
            column(name: 'id', value: 'd807c290-0d31-427c-bffe-0a6a27b3a264')
            column(name: 'category', value: 'COMMON')
            column(name: 'name', value: 'sms_sending_delay')
            column(name: 'value', value: '0')
            column(name: 'type', value: 'INTEGER')
            column(name: 'description', value: 'Задержка при отправке СМС, мс')
        }
    }

    changeSet(id: '2017-03-11-05', author: 'Nikita Shevtsov <shevtsou@gmail.com>') {
        comment("Insert amount per page settings")
        insert(schemaName: 'jsender', tableName: 'setting') {
            column(name: 'id', value: '6b3c3a1a-8332-445d-8e83-2491c5cef5d6')
            column(name: 'category', value: 'VIEW')
            column(name: 'name', value: 'sending_recipients_per_page')
            column(name: 'value', value: '20')
            column(name: 'type', value: 'INTEGER')
            column(name: 'description', value: 'Количество получателей на странице рассылки')
        }
        insert(schemaName: 'jsender', tableName: 'setting') {
            column(name: 'id', value: '1033a628-fe58-4382-9929-0d98d67e8fa8')
            column(name: 'category', value: 'VIEW')
            column(name: 'name', value: 'search_recipients_per_page')
            column(name: 'value', value: '20')
            column(name: 'type', value: 'INTEGER')
            column(name: 'description', value: 'Количество получателей на странице поиска')
        }
        insert(schemaName: 'jsender', tableName: 'setting') {
            column(name: 'id', value: 'f8ea9abe-b5b3-4147-a936-4811d588063c')
            column(name: 'category', value: 'VIEW')
            column(name: 'name', value: 'message_templates_per_page')
            column(name: 'value', value: '20')
            column(name: 'type', value: 'INTEGER')
            column(name: 'description', value: 'Количество шаблонов сообщений на странице')
        }
        insert(schemaName: 'jsender', tableName: 'setting') {
            column(name: 'id', value: '571b6b24-90ae-4f00-8a96-b94e97ccee8f')
            column(name: 'category', value: 'VIEW')
            column(name: 'name', value: 'sent_messages_per_page')
            column(name: 'value', value: '20')
            column(name: 'type', value: 'INTEGER')
            column(name: 'description', value: 'Количество отправленных сообщений на странице')
        }
        insert(schemaName: 'jsender', tableName: 'setting') {
            column(name: 'id', value: '8a6e5c45-d915-493d-ac29-2c5abd0e5977')
            column(name: 'category', value: 'VIEW')
            column(name: 'name', value: 'sending_accounts_per_page')
            column(name: 'value', value: '20')
            column(name: 'type', value: 'INTEGER')
            column(name: 'description', value: 'Количество учетных записей странице')
        }
    }

    changeSet(id: '2017-03-11-06', author: 'Nikita Shevtsov <shevtsou@gmail.com>') {
        comment("Insert requisite settings")
        insert(schemaName: 'jsender', tableName: 'setting') {
            column(name: 'id', value: '445157fe-0ea5-4702-94e3-eb9cd4948024')
            column(name: 'category', value: 'REQUISITE')
            column(name: 'name', value: 'organization_name')
            column(name: 'value', value: '')
            column(name: 'type', value: 'STRING')
            column(name: 'description', value: 'Название организации')
        }
        insert(schemaName: 'jsender', tableName: 'setting') {
            column(name: 'id', value: 'c9a11401-2795-4cac-a172-10ee4ebd5734')
            column(name: 'category', value: 'REQUISITE')
            column(name: 'name', value: 'organization_address')
            column(name: 'value', value: '')
            column(name: 'type', value: 'STRING')
            column(name: 'description', value: 'Адрес организации')
        }
        insert(schemaName: 'jsender', tableName: 'setting') {
            column(name: 'id', value: '3226f6cb-314e-4c7a-91a2-5993469de0b0')
            column(name: 'category', value: 'REQUISITE')
            column(name: 'name', value: 'organization_mobile_phone_number')
            column(name: 'value', value: '')
            column(name: 'type', value: 'STRING')
            column(name: 'description', value: 'Мобильный телефон организации')
        }
    }

    changeSet(id: '2017-03-11-07', author: 'Nikita Shevtsov <shevtsou@gmail.com>') {
        comment("Insert jdent integration settings")
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

    changeSet(id: '2017-03-11-08', author: 'Nikita Shevtsov <shevtsou@gmail.com>') {
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

    changeSet(id: '2017-03-11-09', author: 'Nikita Shevtsov <shevtsou@gmail.com>') {
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
            column(name: 'recipient_id', type: 'VARCHAR(128)', remarks: 'Unique identifier of recipient ') {
                constraints(nullable: false)
            }
            column(name: 'recipient_source', type: 'VARCHAR(10)', remarks: 'The recipient source') {
                constraints(nullable: false)
            }
            column(name: 'recipient_fullname', type: 'VARCHAR(100)', remarks: 'The recipient fullname') {
                constraints(nullable: false)
            }
            column(name: 'contact_info', type: 'VARCHAR(50)', remarks: 'The message contact info') {
                constraints(nullable: false)
            }
            column(name: 'subject', type: 'VARCHAR(250)', remarks: 'Message subject')
            column(name: 'text', type: 'VARCHAR(2000)', remarks: 'Message sent text') {
                constraints(nullable: false)
            }
        }
        addPrimaryKey(schemaName: 'jsender', tableName: 'sent_message', tablespace: 'jsender_index',
                columnNames: 'id', constraintName: 'PK_sent_message')
    }

    changeSet(id: '2017-03-11-10', author: 'Vladimir Zburzhynski <zburzhynski@gmail.com>') {
        createTable(schemaName: 'jsender', tableName: 'param', tablespace: 'jsender_data', remarks: 'Param') {
            column(name: 'id', type: 'VARCHAR(128)', remarks: 'The unique identifier of param') {
                constraints(nullable: false)
            }
            column(name: 'name', type: 'VARCHAR(50)', remarks: 'Param name') {
                constraints(nullable: false)
            }
            column(name: 'type', type: 'VARCHAR(10)', remarks: 'Param type') {
                constraints(nullable: false)
            }
            column(name: 'description', type: 'VARCHAR(200)', remarks: 'Param description') {
                constraints(nullable: false)
            }
        }
        addPrimaryKey(schemaName: 'jsender', tableName: 'param', tablespace: 'jsender_index',
                columnNames: 'id', constraintName: 'PK_param')
    }

    changeSet(id: '2017-03-11-11', author: 'Vladimir Zburzhynski <zburzhynski@gmail.com>') {
        createTable(schemaName: 'jsender', tableName: 'sending_service', tablespace: 'jsender_data', remarks: 'Sending service') {
            column(name: 'id', type: 'VARCHAR(128)', remarks: 'The unique identifier of sending service') {
                constraints(nullable: false)
            }
            column(name: 'name', type: 'VARCHAR(100)', remarks: 'The sending service name') {
                constraints(nullable: false)
            }
            column(name: 'sending_type', type: 'VARCHAR(10)', remarks: 'Service sending type') {
                constraints(nullable: false)
            }
        }
        addPrimaryKey(schemaName: 'jsender', tableName: 'sending_service', tablespace: 'jsender_index',
                columnNames: 'id', constraintName: 'PK_sending_service')
    }

    changeSet(id: '2017-03-11-12', author: 'Nikita Shevtsov <shevtsou@gmail.com>') {
        createTable(schemaName: 'jsender', tableName: 'sending_service_param', tablespace: 'jsender_data', remarks: 'Sending service param') {
            column(name: 'id', type: 'VARCHAR(128)', remarks: 'The unique identifier of sending service param') {
                constraints(nullable: false)
            }
            column(name: 'sending_service_id', type: 'VARCHAR(128)', remarks: 'The reference to sending_service table') {
                constraints(nullable: false)
            }
            column(name: 'param_id', type: 'VARCHAR(128)', remarks: 'The reference to param table') {
                constraints(nullable: false)
            }
            column(name: 'value', type: 'VARCHAR(250)', remarks: 'Sending param value')
        }
        addPrimaryKey(schemaName: 'jsender', tableName: 'sending_service_param', tablespace: 'jsender_index',
                columnNames: 'id', constraintName: 'PK_sending_service_param')
    }

    changeSet(id: '2017-03-11-13', author: 'Nikita Shevtsov <shevtsou@gmail.com>') {
        comment("Added foreign constraint FK_sending_service_param_2_sending_service")
        addForeignKeyConstraint(constraintName: 'FK_sending_service_param_2_sending_service',
                baseTableSchemaName: 'jsender', baseTableName: 'sending_service_param' , baseColumnNames: 'sending_service_id',
                referencedTableSchemaName: 'jsender', referencedTableName: 'sending_service', referencedColumnNames: 'id')
    }

    changeSet(id: '2017-03-11-14', author: 'Vladimir Zburzhynski <zburzhynski@gmail.com>') {
        comment("Added foreign constraint FK_sending_service_param_2_param")
        addForeignKeyConstraint(constraintName: 'FK_sending_service_param_2_param',
                baseTableSchemaName: 'jsender', baseTableName: 'sending_service_param' , baseColumnNames: 'param_id',
                referencedTableSchemaName: 'jsender', referencedTableName: 'param', referencedColumnNames: 'id')
    }

    changeSet(id: '2017-03-11-15', author: 'Nikita Shevtsov <shevtsou@gmail.com>') {
        createTable(schemaName: 'jsender', tableName: 'sending_account', tablespace: 'jsender_data', remarks: 'Sending account') {
            column(name: 'id', type: 'VARCHAR(128)', remarks: 'The unique identifier of sending account') {
                constraints(nullable: false)
            }
            column(name: 'sending_service_id', type: 'VARCHAR(128)', remarks: 'The reference to sending_service table') {
                constraints(nullable: false)
            }
            column(name: 'description', type: 'VARCHAR(1000)', remarks: 'Sending account description')
        }
        addPrimaryKey(schemaName: 'jsender', tableName: 'sending_account', tablespace: 'jsender_index',
                columnNames: 'id', constraintName: 'PK_sending_account')
    }

    changeSet(id: '2017-03-11-16', author: 'Vladimir Zburzhynski <zburzhynski@gmail.com>') {
        comment("Added foreign constraint FK_sending_account_2_sending_service")
        addForeignKeyConstraint(constraintName: 'FK_sending_account_2_sending_service',
                baseTableSchemaName: 'jsender', baseTableName: 'sending_account' , baseColumnNames: 'sending_service_id',
                referencedTableSchemaName: 'jsender', referencedTableName: 'sending_service', referencedColumnNames: 'id')
    }

    changeSet(id: '2017-03-11-17', author: 'Nikita Shevtsov <shevtsou@gmail.com>') {
        createTable(schemaName: 'jsender', tableName: 'sending_account_param', tablespace: 'jsender_data', remarks: 'Sending account param') {
            column(name: 'id', type: 'VARCHAR(128)', remarks: 'The unique identifier of sending account param') {
                constraints(nullable: false)
            }
            column(name: 'sending_account_id', type: 'VARCHAR(128)', remarks: 'The reference to sending_account table') {
                constraints(nullable: false)
            }
            column(name: 'param_id', type: 'VARCHAR(128)', remarks: 'The reference to param table') {
                constraints(nullable: false)
            }
            column(name: 'value', type: 'VARCHAR(250)', remarks: 'Param value') {
                constraints(nullable: false)
            }
        }
        addPrimaryKey(schemaName: 'jsender', tableName: 'sending_account_param', tablespace: 'jsender_index',
                columnNames: 'id', constraintName: 'PK_sending_account_param')
    }

    changeSet(id: '2017-03-11-18', author: 'Nikita Shevtsov <shevtsou@gmail.com>') {
        comment("Added foreign constraint FK_sending_account_param_2_sending_account")
        addForeignKeyConstraint(constraintName: 'FK_sending_account_param_2_sending_account',
                baseTableSchemaName: 'jsender', baseTableName: 'sending_account_param' , baseColumnNames: 'sending_account_id',
                referencedTableSchemaName: 'jsender', referencedTableName: 'sending_account', referencedColumnNames: 'id')
    }

    changeSet(id: '2017-03-11-19', author: 'Nikita Shevtsov <shevtsou@gmail.com>') {
        comment("Added foreign constraint FK_sending_account_param_2_param")
        addForeignKeyConstraint(constraintName: 'FK_sending_account_param_2_param',
                baseTableSchemaName: 'jsender', baseTableName: 'sending_account_param' , baseColumnNames: 'param_id',
                referencedTableSchemaName: 'jsender', referencedTableName: 'param', referencedColumnNames: 'id')
    }

    changeSet(id: '2017-03-11-20', author: 'Nikita Shevtsov <shevtsou@gmail.com>') {
        insert(schemaName: 'jsender', tableName: 'param') {
            column(name: 'id', value: 'a039ad9b-4a0a-4622-a549-ce355c45cea1')
            column(name: 'name', value: 'mail_smtp_host')
            column(name: 'type', value: 'STRING')
            column(name: 'description', value: 'Сервер SMTP')
        }
        insert(schemaName: 'jsender', tableName: 'param') {
            column(name: 'id', value: '5c2c2cbe-c3d3-4161-8daf-c13c6da2b849')
            column(name: 'name', value: 'mail_smtp_port')
            column(name: 'type', value: 'INTEGER')
            column(name: 'description', value: 'Порт SMTP')
        }
        insert(schemaName: 'jsender', tableName: 'param') {
            column(name: 'id', value: '2e995ca7-3bce-4705-85ac-822c6fbd317e')
            column(name: 'name', value: 'user_name')
            column(name: 'type', value: 'STRING')
            column(name: 'description', value: 'Имя пользователя')
        }
        insert(schemaName: 'jsender', tableName: 'param') {
            column(name: 'id', value: '0112434e-3885-4d13-9d6e-14706195fba3')
            column(name: 'name', value: 'user_password')
            column(name: 'type', value: 'PASSWORD')
            column(name: 'description', value: 'Пароль')
        }
        insert(schemaName: 'jsender', tableName: 'param') {
            column(name: 'id', value: '1a4259ce-0797-4ce6-a215-fbb9b2eb62e6')
            column(name: 'name', value: 'token')
            column(name: 'type', value: 'PASSWORD')
            column(name: 'description', value: 'API ключ')
        }
        insert(schemaName: 'jsender', tableName: 'param') {
            column(name: 'id', value: 'ae0843a9-9ee9-4835-b4d1-c2f3bbba0991')
            column(name: 'name', value: 'alphaname_id')
            column(name: 'type', value: 'INTEGER')
            column(name: 'description', value: 'ID альфа-имени')
        }
    }

    changeSet(id: '2017-03-11-21', author: 'Nikita Shevtsov <shevtsou@gmail.com>') {
        comment("Insert gmail.com account")
        insert(schemaName: 'jsender', tableName: 'sending_service') {
            column(name: 'id', value: '06967298-6360-4d8f-af5f-a20d58dd4414')
            column(name: 'name', value: 'gmail.com')
            column(name: 'sending_type', value: 'EMAIL')
        }
        insert(schemaName: 'jsender', tableName: 'sending_service_param') {
            column(name: 'id', value: '0843e81b-bc29-4ff9-8e7f-949df4b20a99')
            column(name: 'sending_service_id', value: '06967298-6360-4d8f-af5f-a20d58dd4414')
            column(name: 'param_id', value: 'a039ad9b-4a0a-4622-a549-ce355c45cea1')
            column(name: 'value', value: 'smtp.gmail.com')
        }
        insert(schemaName: 'jsender', tableName: 'sending_service_param') {
            column(name: 'id', value: '60a7e314-1e73-4b80-8869-cf32cd2835c1')
            column(name: 'sending_service_id', value: '06967298-6360-4d8f-af5f-a20d58dd4414')
            column(name: 'param_id', value: '5c2c2cbe-c3d3-4161-8daf-c13c6da2b849')
            column(name: 'value', value: '587')
        }
        insert(schemaName: 'jsender', tableName: 'sending_service_param') {
            column(name: 'id', value: 'd63ea4f9-a9ee-4a1c-a4d7-ced387194c22')
            column(name: 'sending_service_id', value: '06967298-6360-4d8f-af5f-a20d58dd4414')
            column(name: 'param_id', value: '2e995ca7-3bce-4705-85ac-822c6fbd317e')
        }
        insert(schemaName: 'jsender', tableName: 'sending_service_param') {
            column(name: 'id', value: 'acc18b72-cf5e-4525-acee-32bf1d75abbb')
            column(name: 'sending_service_id', value: '06967298-6360-4d8f-af5f-a20d58dd4414')
            column(name: 'param_id', value: '0112434e-3885-4d13-9d6e-14706195fba3')
        }
    }

    changeSet(id: '2017-03-11-22', author: 'Nikita Shevtsov <shevtsou@gmail.com>') {
        comment("Insert yandex.ru account")
        insert(schemaName: 'jsender', tableName: 'sending_service') {
            column(name: 'id', value: '543e6b2e-cb4d-4b64-a26a-e31820473af2')
            column(name: 'name', value: 'yandex.ru')
            column(name: 'sending_type', value: 'EMAIL')
        }
        insert(schemaName: 'jsender', tableName: 'sending_service_param') {
            column(name: 'id', value: '52dd4cba-08ea-4b1f-b6ff-4674691fd0d8')
            column(name: 'sending_service_id', value: '543e6b2e-cb4d-4b64-a26a-e31820473af2')
            column(name: 'param_id', value: 'a039ad9b-4a0a-4622-a549-ce355c45cea1')
            column(name: 'value', value: 'smtp.yandex.ru')
        }
        insert(schemaName: 'jsender', tableName: 'sending_service_param') {
            column(name: 'id', value: 'c0fcb43b-0a96-4bf1-a5b7-40d366da73a7')
            column(name: 'sending_service_id', value: '543e6b2e-cb4d-4b64-a26a-e31820473af2')
            column(name: 'param_id', value: '5c2c2cbe-c3d3-4161-8daf-c13c6da2b849')
            column(name: 'value', value: '465')
        }
        insert(schemaName: 'jsender', tableName: 'sending_service_param') {
            column(name: 'id', value: '8fb48099-f2df-434c-a273-d9a7bdfd42fc')
            column(name: 'sending_service_id', value: '543e6b2e-cb4d-4b64-a26a-e31820473af2')
            column(name: 'param_id', value: '2e995ca7-3bce-4705-85ac-822c6fbd317e')
        }
        insert(schemaName: 'jsender', tableName: 'sending_service_param') {
            column(name: 'id', value: '63cb3bed-064a-40ec-b32f-fd6c82f2b77d')
            column(name: 'sending_service_id', value: '543e6b2e-cb4d-4b64-a26a-e31820473af2')
            column(name: 'param_id', value: '0112434e-3885-4d13-9d6e-14706195fba3')
        }
    }

    changeSet(id: '2017-03-11-23', author: 'Nikita Shevtsov <shevtsou@gmail.com>') {
        comment("Insert mail.ru account")
        insert(schemaName: 'jsender', tableName: 'sending_service') {
            column(name: 'id', value: 'e152bd93-43c4-4ed8-ac4d-d3a4ea0b509b')
            column(name: 'name', value: 'mail.ru')
            column(name: 'sending_type', value: 'EMAIL')
        }
        insert(schemaName: 'jsender', tableName: 'sending_service_param') {
            column(name: 'id', value: '24f9ec04-e44e-476b-bd63-eaa21f61e22d')
            column(name: 'sending_service_id', value: 'e152bd93-43c4-4ed8-ac4d-d3a4ea0b509b')
            column(name: 'param_id', value: 'a039ad9b-4a0a-4622-a549-ce355c45cea1')
            column(name: 'value', value: 'smtp.mail.ru')
        }
        insert(schemaName: 'jsender', tableName: 'sending_service_param') {
            column(name: 'id', value: '63c8d298-f63f-480e-abfd-2aba62bf82b7')
            column(name: 'sending_service_id', value: 'e152bd93-43c4-4ed8-ac4d-d3a4ea0b509b')
            column(name: 'param_id', value: '5c2c2cbe-c3d3-4161-8daf-c13c6da2b849')
            column(name: 'value', value: '465')
        }
        insert(schemaName: 'jsender', tableName: 'sending_service_param') {
            column(name: 'id', value: '41a39c86-3a3a-42fc-be00-a2d003494cbe')
            column(name: 'sending_service_id', value: 'e152bd93-43c4-4ed8-ac4d-d3a4ea0b509b')
            column(name: 'param_id', value: '2e995ca7-3bce-4705-85ac-822c6fbd317e')
        }
        insert(schemaName: 'jsender', tableName: 'sending_service_param') {
            column(name: 'id', value: '6993e87f-90fb-49be-bcea-648d2591f7eb')
            column(name: 'sending_service_id', value: 'e152bd93-43c4-4ed8-ac4d-d3a4ea0b509b')
            column(name: 'param_id', value: '0112434e-3885-4d13-9d6e-14706195fba3')
        }
    }

    changeSet(id: '2017-03-11-24', author: 'Nikita Shevtsov <shevtsou@gmail.com>') {
        comment("Insert unisender.by account")
        insert(schemaName: 'jsender', tableName: 'sending_service') {
            column(name: 'id', value: 'a12a3869-3f68-49a6-af42-fe999519b5c4')
            column(name: 'name', value: 'sms.unisender.by')
            column(name: 'sending_type', value: 'SMS')
        }
        insert(schemaName: 'jsender', tableName: 'sending_service_param') {
            column(name: 'id', value: '1f679dc9-b88c-4ecb-b4d5-33220fd68df1')
            column(name: 'sending_service_id', value: 'a12a3869-3f68-49a6-af42-fe999519b5c4')
            column(name: 'param_id', value: '1a4259ce-0797-4ce6-a215-fbb9b2eb62e6')
        }
        insert(schemaName: 'jsender', tableName: 'sending_service_param') {
            column(name: 'id', value: 'f914197d-7afb-49b1-a414-c41d5d9e5799')
            column(name: 'sending_service_id', value: 'a12a3869-3f68-49a6-af42-fe999519b5c4')
            column(name: 'param_id', value: 'ae0843a9-9ee9-4835-b4d1-c2f3bbba0991')
            column(name: 'value', value: '0')
        }
    }

    changeSet(id: '2017-03-11-25', author: 'Nikita Shevtsov <shevtsou@gmail.com>') {
        comment("Insert license settings")
        insert(schemaName: 'jsender', tableName: 'setting') {
            column(name: 'id', value: '3e605e9c-774e-4624-9a66-d80cb5befe62')
            column(name: 'category', value: 'LICENSE')
            column(name: 'name', value: 'aaa')
            column(name: 'value', value: 'Vzcs+Qoybk31dJoSKbjrtA==')
            column(name: 'type', value: 'STRING')
            column(name: 'description', value: 'aaa')
        }
        insert(schemaName: 'jsender', tableName: 'setting') {
            column(name: 'id', value: '507bfb8c-bc7d-4d0c-b984-9272460ef441')
            column(name: 'category', value: 'LICENSE')
            column(name: 'name', value: 'bbb')
            column(name: 'value', value: 'J6noq2cLldNAA9uhvJE8Ig==')
            column(name: 'type', value: 'STRING')
            column(name: 'description', value: 'bbb')
        }
        insert(schemaName: 'jsender', tableName: 'setting') {
            column(name: 'id', value: 'cf423e2c-55af-4e75-9323-68a8bbbfd881')
            column(name: 'category', value: 'LICENSE')
            column(name: 'name', value: 'ccc')
            column(name: 'value', value: 'J+s1XnfFxr9sO2aDVT/G3g==')
            column(name: 'type', value: 'STRING')
            column(name: 'description', value: 'ccc')
        }
    }

}