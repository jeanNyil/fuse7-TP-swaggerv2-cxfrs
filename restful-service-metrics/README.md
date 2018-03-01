# RESTful Service Metrics (swagger v2.0) :: Usage of the camel-metrics on service requests

Returns a randomly generated numbers list according to input count and range parameters

## Prerequisites

- _**[Red Hat Fuse 7 Standalone on Apache Karaf Technical Preview](https://access.redhat.com/documentation/en-us/red_hat_jboss_fuse/7.0-tp/html/installing_on_apache_karaf/)**_ is installed 
and running
- The following software are installed:
  - _**[Apache Maven 3+](https://maven.apache.org/)**_
  - The ```curl``` command is available for testing the deployed RESTful service. Otherwise,
    - A web browser may be used or any web service client like _**[SoapUI](https://www.soapui.org/)**_ or _**[Postman](https://www.getpostman.com/)**_
    - I used _**[HTTPie](https://httpie.org/)**_ for my tests.

## Build the project with Apache Maven

1. Go into the **[restful-service-metrics](../restful-service-metrics)** project root folder
2. Build the project using the ```mvn clean install``` command line

## Deploy the restful-service-metrics bundle

1. Log into the Fuse 7 Standalone Apache Karaf console
2. Enter the following command lines to deploy the **[restful-service-metrics](../restful-service-metrics)** bundle
```
feature:repo-add mvn:org.jeannyil.fuse.cxfrs.metrics/restful-service-metrics/1.0.0-SNAPSHOT/xml/features
feature:install restful-service-metrics
```

## Test the restful-service-metrics service

### Assumptions
- My Fuse 7 Standalone on Apache Karaf has the following run-time configuration:
  - host: ```fuse-standalone.lab.com```
  - [undertow](http://undertow.io/) listening port: ```8181```
- Thus, the ```org.jeannyil.fuse.restful-service-metrics.cfg```*PID* is configured as follows:
```
camel.name.route=restful-service-metrics-route
exposed.service.gateway.host=fuse-standalone.lab.com
exposed.service.gateway.port=8181
```

### Request the swagger 2.0 spec

```
$ http 'http://fuse-standalone.lab.com:8181/cxf/rest2/swagger.yaml'
HTTP/1.1 200 OK
Connection: keep-alive
Content-Length: 2069
Content-Type: application/yaml
Date: Thu, 01 Mar 2018 23:12:58 GMT
Server: Pax-HTTP-Undertow
X-Powered-By: Open Source

---
swagger: "2.0"
info:
  description: "Returns a randomly generated numbers list according to input count\
    \ and range parameters"
  version: "1.0.0-SNAPSHOT"
  title: "RESTful Service Metrics (swagger v2.0) :: Usage of the camel-metrics on\
    \ service requests"
  contact:
    name: "jean.nyilimbibi@redhat.com"
  license:
    name: "Apache 2.0 License"
    url: "http://www.apache.org/licenses/LICENSE-2.0.html"
host: "fuse-standalone.lab.com:8181"
basePath: "/cxf/rest2"
tags:
- name: "restfulservice"
schemes:
- "http"
- "https"
paths:
  /restfulservice/generatenumbers:
    get:
      tags:
      - "restfulservice"
      summary: "Returns a list of randomly generated numbers according to the input\
        \ count and range query parameters"
      description: "The returned list associate each generated number with a even/odd\
        \ boolean flag<br>The following required query parameters must be supplied:\
        \ count, range<br>Request URI sample: /restfulservice/generatenumbers?count=2&range=50<br>Corresponding\
        \ JSON response:<br/>{<br>  \"count\": 2,<br>  \"range\": 50,<br>  \"randomlyGeneratedNumbers\"\
        : [<br>    {<br>      \"number\": 20,<br>      \"isEven\": true<br>    },<br>\
        \    {<br>      \"number\": 42,<br>      \"isEven\": true<br>    }<br>  ]<br>}"
      operationId: "getRandomlyGeneratedNumbers"
      produces:
      - "application/json"
      parameters:
      - name: "count"
        in: "query"
        description: "Number of random integers to generate"
        required: true
        type: "integer"
        default: 0
        format: "int32"
      - name: "range"
        in: "query"
        description: "The maximum range for the generated number"
        required: true
        type: "integer"
        default: 0
        format: "int32"
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

### Request with count=3 and range=31031980

```
$ http 'http://fuse-standalone.lab.com:8181/cxf/rest2/restfulservice/generatenumbers?count=3&range=31031980'
HTTP/1.1 200 OK
Accept: */*
Content-Length: 232
Content-Type: application/json
Date: Thu, 01 Mar 2018 23:14:38 GMT
Host: fuse-standalone.lab.com:8181
Server: Pax-HTTP-Undertow
User-Agent: HTTPie/0.9.9
X-Powered-By: Open Source
accept-encoding: gzip, deflate
breadcrumbId: ID-data-server-lab-com-1519943596694-2-1
connection: keep-alive
count: 3
range: 31031980

{
    "count": 3,
    "randomlyGeneratedNumbers": [
        {
            "isEven": true,
            "number": 19662760
        },
        {
            "isEven": false,
            "number": 16001073
        },
        {
            "isEven": false,
            "number": 22627681
        }
    ],
    "range": 31031980
}
```

### Check error response

```
$ http 'http://fuse-standalone.lab.com:8181/cxf/rest2/restfulservice/generatenumbers?count=3'
HTTP/1.1 200 OK
Accept: */*
Content-Length: 82
Content-Type: application/json
Date: Thu, 01 Mar 2018 23:15:14 GMT
Host: fuse-standalone.lab.com:8181
Server: Pax-HTTP-Undertow
User-Agent: HTTPie/0.9.9
X-Powered-By: Open Source
accept-encoding: gzip, deflate
breadcrumbId: ID-data-server-lab-com-1519943596694-2-3
connection: keep-alive
count: 3
range: 0

{
    "errorMessage": "The 'range' query parameter is mandatory and must be > 0!"
}
```
