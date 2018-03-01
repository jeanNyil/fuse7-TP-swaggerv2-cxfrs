# RESTful Service (swagger v2.0) :: IP GeoLocation Service

Leverages the FreeGeoIP RESTful service in order to get the geo-location of an IP address or a hostname.

## Prerequisites

- _**[Red Hat Fuse 7 Standalone on Apache Karaf Technical Preview](https://access.redhat.com/documentation/en-us/red_hat_jboss_fuse/7.0-tp/html/installing_on_apache_karaf/)**_ is installed 
and running
- The following software are installed:
  - _**[Apache Maven 3+](https://maven.apache.org/)**_
  - The ```curl``` command is available for testing the deployed RESTful service. Otherwise,
    - A web browser may be used or any web service client like _**[SoapUI](https://www.soapui.org/)**_ or _**[Postman](https://www.getpostman.com/)**_
    - I used _**[HTTPie](https://httpie.org/)**_ for my tests.

## Build the project with Apache Maven

1. Go into the **[restful-ipservice](../restful-ipservice)** project root folder
2. Build the project using the ```mvn clean install``` command line

## Deploy the restful-ipservice bundle

1. Log into the Fuse 7 Standalone Apache Karaf console
2. Enter the following command lines to deploy the **[restful-ipservice](../restful-ipservice)** bundle
```
feature:repo-add mvn:org.jeannyil.fuse.cxfrs.ipservice/restful-ipservice/1.0.0-SNAPSHOT/xml/features
feature:install restful-ipservice
```

## Test the restful-ipservice

### Assumptions
- My Fuse 7 Standalone on Apache Karaf has the following run-time configuration:
  - host: ```fuse-standalone.lab.com```
  - [undertow](http://undertow.io/) listening port: ```8181```
- Thus, the ```org.jeannyil.fuse.restful-ipservice.cfg```*PID* is configured as follows:
```
camel.name.route=restful-ipservice-route
exposed.service.gateway.host=fuse-standalone.lab.com
exposed.service.gateway.port=8181
```

### Request the swagger 2.0 spec

```
$ http 'http://fuse-standalone.lab.com:8181/cxf/rest1/swagger.yaml'
HTTP/1.1 200 OK
Connection: keep-alive
Content-Length: 2100
Content-Type: application/yaml
Date: Thu, 01 Mar 2018 22:58:55 GMT
Server: Pax-HTTP-Undertow
X-Powered-By: Open Source

---
swagger: "2.0"
info:
  description: "Leverages the FreeGeoIP RESTful service in order to get the geo-location\
    \ of an IP address or a hostname"
  version: "1.0.0-SNAPSHOT"
  title: "RESTful Service (swagger v2.0) :: IP GeoLocation Service"
  contact:
    name: "jean.nyilimbibi@redhat.com"
  license:
    name: "Apache 2.0 License"
    url: "http://www.apache.org/licenses/LICENSE-2.0.html"
host: "fuse-standalone.lab.com:8181"
basePath: "/cxf/rest1"
tags:
- name: "ipservice"
schemes:
- "http"
- "https"
paths:
  /ipservice/geolocation:
    get:
      tags:
      - "ipservice"
      summary: "Get the geo-location for an IP address or a hostname"
      description: "The following optional query parameters can be supplied: type,\
        \ ip<br>The response format depends on the input type parameter: xml or json<br>Request\
        \ URI sample: /ipservice/geolocation?ip=redhat.com&type=json<br>Corresponding\
        \ JSON response:<br>{<br/>    \"ip\": \"209.132.183.105\",<br>    \"country_code\"\
        : \"US\",<br>    \"country_name\": \"United States\",<br>    \"region_code\"\
        : \"NC\",\n    \"region_name\": \"North Carolina\",<br>    \"city\": \"Raleigh\"\
        ,<br>    \"zip_code\": \"27601\",<br>    \"time_zone\": \"America/New_York\"\
        ,<br>    \"latitude\": 35.7716,<br>    \"longitude\": -78.6356,<br>    \"\
        metro_code\": 560<br>}"
      operationId: "getGeoLocation"
      produces:
      - "application/json"
      - "application/xml"
      parameters:
      - name: "type"
        in: "query"
        description: "Expected response format: xml or json - default: json"
        required: false
        type: "string"
        default: "json"
      - name: "ip"
        in: "query"
        description: "IP address or hostname - default: empty"
        required: false
        type: "string"
      responses:
        200:
          description: "successful operation"
          schema:
            type: "string"
        400:
          description: "Invalid input parameters"
        500:
          description: "Internal server error"
```

### Request the geo-location for redhat.com

```
$ http 'http://fuse-standalone.lab.com:8181/cxf/rest1/ipservice/geolocation?ip=redhat.com'
HTTP/1.1 200 OK
CF-RAY: 3f4f3e7d10c0a8d5-CDG
Content-Length: 250
Content-Type: application/json
Date: Thu, 01 Mar 2018 23:01:11 GMT
Server: cloudflare
Set-Cookie: __cfduid=dd25ac7ddf4004e31b3c1e942347a49581519945271; expires=Fri, 01-Mar-19 23:01:11 GMT; path=/; domain=.freegeoip.net; HttpOnly
Vary: Origin
X-Database-Date: Fri, 16 Feb 2018 08:33:37 GMT
X-Powered-By: Open Source
connection: keep-alive
type: json

{
    "city": "Raleigh",
    "country_code": "US",
    "country_name": "United States",
    "ip": "209.132.183.105",
    "latitude": 35.7716,
    "longitude": -78.6356,
    "metro_code": 560,
    "region_code": "NC",
    "region_name": "North Carolina",
    "time_zone": "America/New_York",
    "zip_code": "27601"
}
```

### Check error response

```
$ http 'http://fuse-standalone.lab.com:8181/cxf/rest1/ipservice/geolocation?ip=redhat.com&type=csv'
HTTP/1.1 200 OK
Accept: */*
Content-Length: 224
Content-Type: application/json
Date: Thu, 01 Mar 2018 23:02:49 GMT
Host: fuse-standalone.lab.com:8181
Server: Pax-HTTP-Undertow
User-Agent: HTTPie/0.9.9
X-Powered-By: Open Source
accept-encoding: gzip, deflate
breadcrumbId: ID-data-server-lab-com-1519943596694-1-4
connection: keep-alive
ip: redhat.com
type: csv

{
    "errorMessage": "[csv] parameter is not valid value for type parameter! - possible values: [xml, json]",
    "inputParameters": {
        "ip": "redhat.com",
        "type": "csv"
    },
    "ip": "redhat.com",
    "type": "csv"
}
```
