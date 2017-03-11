databaseChangeLog {

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


}