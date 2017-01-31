databaseChangeLog {

    changeSet(id: '2017-01-31-02', author: 'Nikita Shevtsov <shevtsou@gmail.com>') {
        createTable(tableName: 'setting', tablespace: 'jsender_data', remarks: 'Application settings') {
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
            column(name: 'state', type: 'INTEGER', defaultValue: '0', remarks: 'The contact info email state') {
                constraints(nullable: false)
            }
            column(name: 'state_date', type: 'TIMESTAMP', defaultValue: 'now()', remarks: 'The state date') {
                constraints(nullable: false)
            }
            column(name: 'user_id', type: 'VARCHAR(128)', defaultValue: '', remarks: 'The reference to the user table') {
                constraints(nullable: false)
            }
        }
        addPrimaryKey(tableName: 'setting', tablespace: 'jsender_index',
                columnNames: 'id', constraintName: 'PK_setting')
    }

    changeSet(id: '2017-01-31-03', author: 'Nikita Shevtsov <shevtsou@gmail.com>') {
        comment("Create unique constraint on setting table")
        sql(stripComments: true, splitStatements: false, endDelimiter: ';') {
            """
            CREATE UNIQUE INDEX UK_setting_name ON setting (name) WHERE state <> '2';
            """
        }
    }

    changeSet(id: '2017-01-31-04', author: 'Nikita Shevtsov <shevtsou@gmail.com>') {
        insert(tableName: 'setting') {
            column(name: 'id', value: 'a039ad9b-4a0a-4622-a549-ce355c45cea1')
            column(name: 'category', value: 'EMAIL_SENDING')
            column(name: 'name', value: 'mail_smtp_host')
            column(name: 'value', value: 'smtp.gmail.com')
            column(name: 'type', value: 'STRING')
            column(name: 'description', value: 'Сервер SMTP')
            column(name: 'state', value: '0')
            column(name: 'state_date', valueDate: 'now()')
            column(name: 'user_id', value: '6fa1b0d0-0b24-4c3c-b7a7-f50afffa4cc3')
        }
        insert(tableName: 'setting') {
            column(name: 'id', value: '5c2c2cbe-c3d3-4161-8daf-c13c6da2b849')
            column(name: 'category', value: 'EMAIL_SENDING')
            column(name: 'name', value: 'mail_smtp_port')
            column(name: 'value', value: '587')
            column(name: 'type', value: 'STRING')
            column(name: 'description', value: 'Порт SMTP')
            column(name: 'state', value: '0')
            column(name: 'state_date', valueDate: 'now()')
            column(name: 'user_id', value: '6fa1b0d0-0b24-4c3c-b7a7-f50afffa4cc3')
        }
        insert(tableName: 'setting') {
            column(name: 'id', value: '2e995ca7-3bce-4705-85ac-822c6fbd317e')
            column(name: 'category', value: 'EMAIL_SENDING')
            column(name: 'name', value: 'mail_user_name')
            column(name: 'value', value: 'username')
            column(name: 'type', value: 'STRING')
            column(name: 'description', value: 'Имя пользователя')
            column(name: 'state', value: '0')
            column(name: 'state_date', valueDate: 'now()')
            column(name: 'user_id', value: '6fa1b0d0-0b24-4c3c-b7a7-f50afffa4cc3')
        }
        insert(tableName: 'setting') {
            column(name: 'id', value: '0112434e-3885-4d13-9d6e-14706195fba3')
            column(name: 'category', value: 'EMAIL_SENDING')
            column(name: 'name', value: 'mail_password')
            column(name: 'value', value: 'password')
            column(name: 'type', value: 'PASSWORD')
            column(name: 'description', value: 'Пароль')
            column(name: 'state', value: '0')
            column(name: 'state_date', valueDate: 'now()')
            column(name: 'user_id', value: '6fa1b0d0-0b24-4c3c-b7a7-f50afffa4cc3')
        }
    }

}