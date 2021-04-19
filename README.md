# Simple REST API - JavaScript Frameworks

REST API serves for registration of JavaScript Workbooks

## Installation

Download repository from [GitHub](https://github.com/andy6/etnetera) and run gradle command:

```bash
./gradlew bootRun
```

## Usage

Show list of stored JavaScript Frameworks
```bash
GET /frameworks
```

Search Javascript Frameworks by framework name, framework version and deprecation date.
```bash
GET /search?name=React&version=1.0.1&deprecated_after=2011-03-02
```

Show JavaScript Framework with specific an identifier.
```json
GET /{id}
     {id} is an identifier of framework
```

Insert new Javascript Framework
```json
POST /

  {
    "name": "ReactJS",
    "deprecation_date": "2031-03-02",
    "hype_level": "GOOD",
    "versions": [
      {
        "label": "1.0.1",
        "release_date": "2011-04-05"
      },
      {
        "label": "1.2.3-RELEASE",
        "release_date": "2013-03-02"
      }
    ]
  }
```


Update Javascript Framework
```json
PUT /{id}
     {id} is identifier of framework

  {
    "name": "Updated-ReactJS",
    "deprecation_date": "2029-04-05",
    "hype_level": "ENJOY",
    "versions": [
      {
        "label": "1.0.1",
        "release_date": "2011-04-05"
      },
      {
        "label": "1.2.3-RELEASE",
        "release_date": "2013-03-02"
      }
    ]
  }
```


Delete Javascript Framework
```json
DELETE /{id}
     {id} is an identifier of framework
```

## License
[GNU General Public License v3.0](https://choosealicense.com/licenses/gpl-3.0/)