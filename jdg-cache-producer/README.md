## Offload your database data into Red Hat JBoss Data Grid made easy

This project is an example showing how to read data from a PostgreSQL database and put the data into a cache within a Red Hat JBoss Data Grid environment.

### Prerequisites

1. Install [Maven](http://maven.apache.org/install.html)
2. Install [Red Hat JBoss Data Grid](http://developers.redhat.com/products/datagrid/hello-world)
3. Install [PostgreSQL](https://wiki.postgresql.org/wiki/Detailed_installation_guides)

### Build application jdg-cache-producer

```bash
$ mvn -s settings.xml clean install
```

### Run application jdg-cache-producer

```bash
$ mvn -s settings.xml exec:java
```


