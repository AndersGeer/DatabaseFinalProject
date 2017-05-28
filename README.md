[![Build Status](https://travis-ci.org/flaps16/DatabaseFinalProject.svg?branch=master)](https://travis-ci.org/flaps16/DatabaseFinalProject)
# DatabaseFinalProject
by Anders Geer Jakobsen and Mohammed Salameh

This is a schoold project on the PBA of Copenhagne Business Academy, Lyngby for the Software Development PBAs.
The project reads through gutenberg books to find all city names mentioned and puts them into a csv file to be imported into our chosen database systems (Neo4J and MongoDB).

We then compare the speed of various tasks in these Databases to learn more about their strengths and weaknesses.
```
Types of queries
1. Given a city name your application returns all book titles with corresponding authors that mention this city.
2. Given a book title, your application plots all cities mentioned in this book onto a map.
3. Given an author name your application lists all books written by that author and plots all cities mentioned in any of the books onto a map.
4. Given a geolocation, your application lists all books mentioning a city in vicinity of the given geolocation.

* The input data for your application are all English books from Project Gutenberg, see http://www.gutenberg.org. 
Amongst others, they provide public domain books as plain text files. A description on how to automatically 
download these books is given here: 
https://www.exratione.com/2014/11/how-to-politely-download-all-english-language-text-format-files-from-project-gutenberg/. 
Based on this, I provide a machine configuration which allows you to download all the books via a Digitalocean Droplet
https://github.com/HelgeCPH/db_course_nosql/tree/master/book_download. 
Alternatively, you can get all the books from a USB key in class.
* A CSV file with many cities and their geolocations is avalable from www.geonames.org http://download.geonames.org/export/dump/ 
where we are especially interested in file http://download.geonames.org/export/dump/cities15000.zip or
http://download.geonames.org/export/dump/cities5000.zip
* You write a program that scans each book, i.e., each text file and extracts all city names. 
For this task a heuristic is good enough. That is, it is okay if you miss cities with extravagant names.
* Your databases store thus, author names, book titles, and names of cities, their geolocations and their occurences in texts.
* The frontend of your application does not matter too much. That is, a CLI application is as well as a web-application.
```

### Query 1:
Please note all mongo tests are run through RoboMongo, as the server would refuse incoming connections even after changing the config file and restarting the service. 
Tests are done on small data set (305 books) with an index on City name in the City collection

`db.Books.find({cities:"New York"}, { title: 1, author: 1 })`
`MATCH (:Book)-[:MENTIONS]-(c:City) WHERE c.name = "New York"
return c`

| Tests - "New York"| Run 1 | Run 2 | Run 3 | Run 4 | Run 5 |
| :-:               |:-:    | :-:   |:-:    |  :-:  |  :-:  |
| MongoDb           | 26ms  | 25ms  |  28ms |25ms   |  26ms |
| neo4j             | 1ms   | 1ms   |  1ms  |1ms    |  1ms  |
|Results            | 0     | 0     |0      | 0     |  0    |

`db.Books.find({cities:"God"}, { title: 1, author: 1 })`
`MATCH (:Book)-[:MENTIONS]-(c:City) WHERE c.name = "God"
return c`

| Tests - "God"     | Run 1 | Run 2 | Run 3 | Run 4 | Run 5 |
| :-:               |:-:    | :-:   |:-:    |  :-:  |  :-:  |
| MongoDb           | 54ms  | 52ms  |  51ms |52ms   |  52ms |
| neo4j             | 1ms   | 1ms   |  4ms  |1ms    |  1ms  |
|Results MongoDb    | 219   | 219   |219    | 219   |  219  |
|Results Neo4J      | 163   | 163   |163    | 163   |  163  |

`db.Books.find({cities:"Of"}, { title: 1, author: 1 })`
`MATCH (:Book)-[:MENTIONS]-(c:City) WHERE c.name = "Of"
return c`

| Tests - "Of"     | Run 1 | Run 2 | Run 3 | Run 4 | Run 5 |
| :-:               |:-:    | :-:   |:-:    |  :-:  |  :-:  |
| MongoDb           | 52ms  | 52ms  |  56ms |54ms   |  51ms |
| neo4j             | 1ms   | 4ms   |  4ms  |4ms    |  1ms  |
|Results MongoDb    | 194   | 194   |194    | 194   |  194  |
|Results Neo4J      | 63    | 63    |63     | 63    |  63   |

`db.Books.find({cities:"Preston"}, { title: 1, author: 1 })`
`MATCH (:Book)-[:MENTIONS]-(c:City) WHERE c.name = "Preston"
return c`

| Tests - "Preston" | Run 1 | Run 2 | Run 3 | Run 4 | Run 5 |
| :-:               |:-:    | :-:   |:-:    |  :-:  |  :-:  |
| MongoDb           | 27ms  | 27ms  |  37ms |27ms   |  28ms |
| neo4j             | 1ms   | 4ms   |  1ms  |4ms    |  1ms  |
|Results MongoDb    | 16    | 16    |16     | 16    |  16   |
|Results Neo4J      | 4     | 4     |4      | 4     |  4    |

`db.Books.find({cities:"Frascati"}, { title: 1, author: 1 })`
`MATCH (:Book)-[:MENTIONS]-(c:City) WHERE c.name = "Frascati"
return c`

| Tests - "Frascati"| Run 1 | Run 2 | Run 3 | Run 4 | Run 5 |
| :-:               |:-:    | :-:   |:-:    |  :-:  |  :-:  |
| MongoDb           | 28ms  | 26ms  |  25ms |27ms   |  26ms |
| neo4j             | 1ms   | 1ms   |  1ms  |1ms    |  1ms  |
|Results            | 1     | 1     |1      | 1     |  1    |

Averages:

| Tests     | "New York"    | "God"     | "Of"  |"Preston"  |"Frascati" |
| :-:       |:-:            | :-:       |:-:    |:-:        |:-:        |
| MongoDb   | 26ms          | 52.2ms    |53ms   |29.2ms     |26.4ms     |
| neo4j     | 1ms           | 1.6ms     |2.8ms  |2.2ms      |1ms        |

Medians:

| Tests     | "New York"    | "God"     | "Of"  |"Preston"  |"Frascati" |
| :-:       |:-:            | :-:       |:-:    |:-:        |:-:        |
| MongoDb   | 26ms          | 52ms      |52ms   |27ms       |26ms       |
| neo4j     | 1ms           | 1ms       |4ms    |1ms        |1ms        |

### Query 2: 

Not applicable, for automation, slowness of user moving data from RoboMongo to our API would alow the process down.

`db.Books.find({title:"-UserInput-"}, { cities: 1)`
Given this document, we can then extract the city data and query our City collection for lat and lng
`db.City.find({name:"-CityName-"},{lat:1, lng:1})`
Returns latitude and longitude to be passed on to our connection to the google API for map generation.

**Cypher**: `MATCH (b:Book)-[:MENTIONS]-(c:City) WHERE b.title = "White Queen of the Cannibals: The Story of Mary Slessor"
return c`

| Tests - "White Queen of the Cannibals: The Story of Mary Slessor"| Run 1 | Run 2 | Run 3 | Run 4 | Run 5 |
| :-:               |:-:    | :-:   |:-:    |  :-:  |  :-:  |
| MongoDb           | ms    | ms    |  ms   |ms     |  ms   |
| neo4j             | 1ms   | 2ms   |  4ms  |1ms    |  1ms  |
|Results MongoDb    |       |       |       |       |       |
|Results Neo4J      | 6     | 6     |6      | 6     |  6    |

**Cypher**: `MATCH (b:Book)-[:MENTIONS]-(c:City) WHERE b.title = "The Way of Peace"
return c`

| Tests - "The Way of Peace"| Run 1 | Run 2 | Run 3 | Run 4 | Run 5 |
| :-:               |:-:    | :-:   |:-:    |  :-:  |  :-:  |
| MongoDb           | ms    | ms    |  ms   |ms     |  ms   |
| neo4j             | 1ms   | 4ms   |  1ms  |1ms    |  1ms  |
|Results MongoDb    |       |       |       |       |       |
|Results Neo4J      | 5     | 5     |5      | 5     |  5    |

**Cypher**: `MATCH (b:Book)-[:MENTIONS]-(c:City) WHERE b.title = "The Anatomy of Melancholy"
return c`

| Tests - "The Anatomy of Melancholy"| Run 1 | Run 2 | Run 3 | Run 4 | Run 5 |
| :-:               |:-:    | :-:   |:-:    |  :-:  |  :-:  |
| MongoDb           | ms    | ms    |  ms   |ms     |  ms   |
| neo4j             | 1ms   | 4ms   |  1ms  |1ms    |  1ms  |
|Results MongoDb    |       |       |       |       |       |
|Results Neo4J      | 1     | 1     |1      | 1     |  1    |

**Cypher**: `MATCH (b:Book)-[:MENTIONS]-(c:City) WHERE b.title = "Sea-Power and Other Studies"
return c`

| Tests - "Sea-Power and Other Studies"| Run 1 | Run 2 | Run 3 | Run 4 | Run 5 |
| :-:               |:-:    | :-:   |:-:    |  :-:  |  :-:  |
| MongoDb           | ms    | ms    |  ms   |ms     |  ms   |
| neo4j             | 4ms   | 1ms   |  2ms  |1ms    |  4ms  |
|Results MongoDb    |       |       |       |       |       |
|Results Neo4J      | 10    | 10    |10     | 10    |  10   |
---
**Cypher**: `MATCH (b:Book)-[:MENTIONS]-(c:City) WHERE b.title = "With Kelly to Chitral"
return c`

| Tests - "With Kelly to Chitral"| Run 1 | Run 2 | Run 3 | Run 4 | Run 5 |
| :-:               |:-:    | :-:   |:-:    |  :-:  |  :-:  |
| MongoDb           | ms    | ms    |  ms   |ms     |  ms   |
| neo4j             | 1ms   | 1ms   |  2ms  |1ms    |  2ms  |
|Results MongoDb    |       |       |       |       |       |
|Results Neo4J      | 3     | 3     |3      | 3     |  3    |

Averages:

| Tests     | "White Queen..."    | "The Way..."     | "The Anatomy..."  |"Sea-Power..."  |"With Kelly..." |
| :-:       |:-:            | :-:       |:-:    |:-:        |:-:        |
| MongoDb   | ms            | ms        |ms     |ms         |ms         |
| neo4j     | 1.8ms         | 1.6ms     |1.6ms  |2.4ms      |1.4ms      |

Medians:

| Tests     | "White Queen..."    | "The Way..."     | "The Anatomy..."  |"Sea-Power..."  |"With Kelly..." |
| :-:       |:-:            | :-:       |:-:    |:-:        |:-:        |
| MongoDb   | ms            | ms        |ms     |ms         |ms         |
| neo4j     | 1ms           | 1ms       |1ms    |2ms        |1ms        |


### Query 3:
Similarly combines Query 1 and 2 somewhat.
`db.Books.find({author:"-UserInput-"}, { title: 1, cities: 1)`
To send on cities to our City collection and thus get latitude and longitude
`db.City.find({name:"-CityName-"},{lat:1, lng:1})`


**Cypher**: `MATCH (b:Book),(c:City) WHERE b.author = "Annie E. Keeling"
MATCH (b)-[:MENTIONS]-(c)
return b,c`

| Tests - "Annie E. Keeling"| Run 1 | Run 2 | Run 3 | Run 4 | Run 5 |
| :-:               |:-:    | :-:   |:-:    |  :-:  |  :-:  |
| MongoDb           | ms    | ms    |  ms   |ms     |  ms   |
| neo4j             | 1ms   | 1ms   |  1ms  |4ms    |  1ms  |
|Results MongoDb    |       |       |       |       |       |
|Results Neo4J      | 3     | 3     |3      | 3     |  3    |

**Cypher**: `MATCH (b:Book),(c:City) WHERE b.author = "Leigh Hunt"
MATCH (b)-[:MENTIONS]-(c)
return b,c`

| Tests - "Leigh Hunt"| Run 1 | Run 2 | Run 3 | Run 4 | Run 5 |
| :-:               |:-:    | :-:   |:-:    |  :-:  |  :-:  |
| MongoDb           | ms    | ms    |  ms   |ms     |  ms   |
| neo4j             | 1ms   | 1ms   |  4ms  |1ms    |  4ms  |
|Results MongoDb    |       |       |       |       |       |
|Results Neo4J      | 5     | 5     |5      | 5     |  5    |

**Cypher**: `MATCH (b:Book),(c:City) WHERE b.author = "Various"
MATCH (b)-[:MENTIONS]-(c)
return c`

| Tests - "Various"| Run 1 | Run 2 | Run 3 | Run 4 | Run 5 |
| :-:               |:-:    | :-:   |:-:    |  :-:  |  :-:  |
| MongoDb           | ms    | ms    |  ms   |ms     |  ms   |
| neo4j             | 3ms   | 4ms   |  4ms  |4ms    |  4ms  |
|Results MongoDb    |       |       |       |       |       |
|Results Neo4J      | 148   | 148   |148    | 148   |  148  |

**Cypher**: `MATCH (b:Book),(c:City) WHERE b.author = "Zane Grey"
MATCH (b)-[:MENTIONS]-(c)
return c`

| Tests - "Zane Grey"| Run 1 | Run 2 | Run 3 | Run 4 | Run 5 |
| :-:               |:-:    | :-:   |:-:    |  :-:  |  :-:  |
| MongoDb           | ms    | ms    |  ms   |ms     |  ms   |
| neo4j             | 1ms   | 1ms   |  1ms  |1ms    |  1ms  |
|Results MongoDb    |       |       |       |       |       |
|Results Neo4J      | 4     | 4     |4      | 4     |  4    |
---
**Cypher**: `MATCH (b:Book),(c:City) WHERE b.author = "Editor-in-Chief: Rossiter Johnson"
MATCH (b)-[:MENTIONS]-(c)
return c`

| Tests - "Editor-in-Chief: Rossiter Johnson"| Run 1 | Run 2 | Run 3 | Run 4 | Run 5 |
| :-:               |:-:    | :-:   |:-:    |  :-:  |  :-:  |
| MongoDb           | ms    | ms    |  ms   |ms     |  ms   |
| neo4j             | 1ms   | 4ms   |  1ms  |4ms    |  1ms  |
|Results MongoDb    |       |       |       |       |       |
|Results Neo4J      | 39    | 39    |39     | 39    |  39    |

Averages:

| Tests     | "Annie E. Keeling"    | "Leigh Hunt"     | "Various"  |"Zane Grey"  |"Editor-in-Chief: Rossiter Johnson" |
| :-:       |:-:            | :-:       |:-:    |:-:        |:-:        |
| MongoDb   | ms            | ms        |ms     |ms         |ms         |
| neo4j     | 1ms           | 2.2ms     |3.8ms  |2.4ms      |2.2ms      |

Medians:

| Tests     | "Annie E. Keeling"    | "Leigh Hunt"     | "Various"  |"Zane Grey"  |"Editor-in-Chief: Rossiter Johnson" |
| :-:       |:-:            | :-:       |:-:    |:-:        |:-:        |
| MongoDb   | ms            | ms        |ms     |ms         |ms         |
| neo4j     | 1.6ms         | 1ms       |4ms    |1ms        |1ms        |

### Query 4:
One way of doing it in mongodb 
`db.City.find({lat:{"$gt": ~(Lat-1)~}, lat:{"$lt": ~(Lat+1)~},lng:{"$gt": ~(lng-1)~},lng:{"$lt": ~(Lng+1)~}},{name:1})`


## Descriptions

### **Design**

Neo4J Graph/datamodel

**Book**

	 Create node _Book_: 
	* `CREATE (b:Book{title: "HelloWorld", author: "IoT" })` 
	* `CREATE INDEX ON :Book(title)` 	

**City**
Create node _City_: 
* `CREATE (c:City{name: "Roskilde", latitude:55.64152, longitude: 12.08035 })` 
* `CREATE INDEX ON :City(name)` 

**Relationship from Book to city**
* Adding _MENTIONS_ relationship from Books -> City.
```
EXPLAIN MATCH (b:Book),(c:City)
WHERE b.title = 'HelloWorld' AND c.name = 'Roskilde'
CREATE (b)-[m:MENTIONS]->(c)
RETURN m
```

### **Importing to Neo4J**:
We had some issues connecting through Neo4J Droplet and getting access to its local Database, this prevented us from running our Query’s _online_, but everything we measured was local, both mongodb and neo4j ran locally on our PC's just for good measure, since request/response from the DB's online would show inconsistent measurements if 1 of the databases were online.
CSV files needed
	 * bookMentionsCityFinal.csv
	 * booksFinal.csv
	 * citiesFinal.csv

#### Creating the Book nodes fron _booksFinal.csv_: 
`LOAD CSV WITH HEADERS FROM "file:///citiesFinal.csv" AS line
CREATE (:Book {id: tointeger(line.bookid), title: line.title, lat: line.lat, lng: line.lng })
`

#### Creating the City nodes fron _citiesFinal.csv_: 
`LOAD CSV WITH HEADERS FROM "file:///citiesFinal.csv" AS line
CREATE (:City {id: tointeger(line.cityid), name: line.name, author: line. })
`

#### Create the **Relationships** from the _bookMentionsCityFinal.csv_:
`LOAD CSV WITH HEADERS FROM "file:///bookMentionsCityFinal.csv" AS line
MATCH (b:Book {id: line.bookid})
MATCH (c:City {id: line.cityid})
MERGE (b)-[:MENTIONS]->(c)`

Although we chose to use Cypher giving us: **781** nodes and **1305** relationships. This could also be done through a Batch script, if large datasets are used. We were planning to use a dataset with over 100k+ nodes and relations, but sadly we did not have time to implement everything. But, if needed, the batch imports data very quick and neo4J allows it through the shown script: (/please NOTE: the tool is located in /pathToNeo4J/bin/neo4J-import, that the neo4j-import will be deprecated and neo4j-admin import will be used instead/) and is used as follows: 
```
neo4j-import --into data/databases/graph.db 
--nodes import/booksFinal.csv 
--nodes import/citiesFinal.csv 
--relationships:MENTIONS import/bookMentionsCityFinal.csv
```


- MongoDB datamodel - Book

```
{
  title: "HelloWorld",
  author: "IoT",
  cities: [
      name: "Roskilde",
      name: "Holte"
  ]
}
```

- MongoDB datamodel - City

```
{
  name: "Roskilde",
  lat: 55.64152,
  lng: 12.08035
}
```



We started thinking about aggregating our data to require less database calls, however issues with imports made us decide to go with several, due to time constraints - we do think one single call is more efficient.

~~We chose to aggregate our data so ONLY 1 single call to the database is required, rather than having to JOIN multiple documents to respond to a query.~~

Importing to Mongodb
The import to mongo db was done with two csv files, one for books and one for cities, with cities having a uniqueness constraint on city names.

```
title,author,cities
"White Queen of the Cannibals: The Story of Mary Slessor","A. J. Bueltmann","Come.Mojo.Dundee.Wright.God.Robertson"
"The Father of British Canada: A Chronicle of Carleton","William Wood","Montmorency.Magna.Livingston.Forster.Anne.Versa$"The Secret of the Tower","Hope, Anthony","Can.Since.Come.Best.Of.Much.God"
...
``` 

```
id,name,lat,lng
1607,Mojo,8.58679,39.12111
2187,God,47.68324,19.13417
7625,Robertson,-33.80342,19.88537
...
``` 

These files allowed us to create the collecitons easily after a quick splitting of city names with a script on the books data - this was done after deleting all entries without any known cities mentioned or the script would fail - and these books are reasonably boring to have in the database, and could give problems later as well.


##Neo4j vs. Mongodb
### Neo4J
Is a Graph database, that builds upon a graph model with labeled Nodes, connections(directed) & typed relationships. Both nodes and relationships, work like a Key-value pair, making it easy to navigate. Also the representation of the database through nodes and its relationships, is easy to understand for both Developer and Stakeholders to understand the Diagram.
The query language for Neo4j "Cypher" is easy to understand and has the same WHERE,FROM,AND,IN ... Keywords that we know from RDBMS. Neo4j shines when it comes to following relavant relationships to accumulate interesting results.

### MongoDb
Generally we felt like that Mongo should be used when you expect a **High Write Load**, since it actually by default preresr high _insert_ rates. Also common MySQL tables reaching 5-10 GB or over per table, you need to partition your data and shard your database, MongoDb has a built in solution for that. Also one of the strength of Mongo that we felt was a good factor in the comparison to Neo4J, was that its **Schema-less**, adding a new field, does not affect old rows or Documents.

### Conclusion
We do not think there is a best solution for this project, since it all comes down to how efficient your data is build, but following our query results, when it comes to datasets that is not that large, Neo4J beats MongoDb, but the other way around Neo4j Struggles, and it all comes down to how Contraints and Indexes are designed! As for mongoDb, we find it hard to create complex quiries like in Neo4J, but that may be because of our time contraints


**EXTRA: Implementation of different methods inside&Outside our project**
- Use [Google Places API](https://developers.google.com/places/web-service/get-api-key) to retrieve an image from latitude, longitude and radius.
- Function that compares the extracted strings from txt’s to CSV (Common data format)

- Extract Cities from [CSV](http://download.geonames.org/export/dump/cities5000.zip) Name (ASCII), longitude and latitude. 
The extraction was done with _Terminal_, by reading the CSV file with 
`cat cities5000.txt | awk '{print $3" "$5" "$6}'`
_$3_ for the ASCII name, _$5_ for the latitude, _$6_ for the longitude.
Then we needed a Regular Expression so we could sort it and only get the specific format the CSV file had. The above command only prints out the 3rd, 5th and 6th column, but there is (only _latitude_ ($5) and _longitude_ ($6)) specific rows that had a different values, this would be quite hard to manage manually, hence our regular expression:
`grep -Eo '([aA-z0-9]*)\s-?(90|[0-8]?[0-9]\.[0-9]{0,6})\s\-?(180|(1[0-7][0-9]|[0-9]{0,2})\.[0-9]{0,6})' > SortedCities.csv`

 - Parsing book lines is done line by line and in it's ucrrent state only accepts title in the form `title:` to match the title field in about 320 of the 350 books we have imported, likewise for author. - Some titles and authors are listed as `unknown title #1` and `ùnknown author #1` and upwards to allow for such books and titles. The parser itself the reads lines by themselves and checks any words with a capital letter if contained in our dataset of citynames. Similarly the book is considered started and ended at the `Start of this project gutenberg ebook` and `End of this project gutenberg ebook` to allow for simpler parsing. 
The parser does have a few constraints put in place to get as few false positives as possible:
    * Ignores all words at the beginning of a sentence
        * While this can miss some names or even get partial names, we don't consider this a massive issue, but might create other false positive scenarios
    * Checks for a few predefined common prefixes to allow for city names such as New York, San Diego etc.
    * Ignores ALL special characters
        * This does miss a few names, such as spanish cities using `ñ` or french cities using `ç`  and similar


