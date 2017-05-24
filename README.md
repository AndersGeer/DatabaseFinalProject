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

| Tests - "New York"| Run 1 | Run 2 | Run 3 | Run 4 | Run 5 |
| :-:               |:-:    | :-:   |:-:    |  :-:  |  :-:  |
| MongoDb           | 26ms  | 25ms  |  28ms |25ms   |  26ms |
| neo4j             |       |       |       |       |       |
|Results            | 0     | 0     |0      | 0     |  0    |

`db.Books.find({cities:"God"}, { title: 1, author: 1 })`

| Tests - "God"     | Run 1 | Run 2 | Run 3 | Run 4 | Run 5 |
| :-:               |:-:    | :-:   |:-:    |  :-:  |  :-:  |
| MongoDb           | 54ms  | 52ms  |  51ms |52ms   |  52ms |
| neo4j             |       |       |       |       |       |
|Results            | 219   | 219   |219    | 219   |  219  |

`db.Books.find({cities:"Of"}, { title: 1, author: 1 })`

| Tests - "Of"     | Run 1 | Run 2 | Run 3 | Run 4 | Run 5 |
| :-:               |:-:    | :-:   |:-:    |  :-:  |  :-:  |
| MongoDb           | 52ms  | 52ms  |  56ms |54ms   |  51ms |
| neo4j             |       |       |       |       |       |
|Results            | 194   | 194   |194    | 194   |  194  |

`db.Books.find({cities:"Preston"}, { title: 1, author: 1 })`

| Tests - "Preston" | Run 1 | Run 2 | Run 3 | Run 4 | Run 5 |
| :-:               |:-:    | :-:   |:-:    |  :-:  |  :-:  |
| MongoDb           | 27ms  | 27ms  |  37ms |27ms   |  28ms |
| neo4j             |       |       |       |       |       |
|Results            | 16    | 16    |16     | 16    |  16   |

`db.Books.find({cities:"Frascati"}, { title: 1, author: 1 })`

| Tests - "Frascati"| Run 1 | Run 2 | Run 3 | Run 4 | Run 5 |
| :-:               |:-:    | :-:   |:-:    |  :-:  |  :-:  |
| MongoDb           | 28ms  | 26ms  |  25ms |27ms   |  26ms |
| neo4j             |       |       |       |       |       |
|Results            | 1     | 1     |1      | 1     |  1    |

Averages:

| Tests     | "New York"    | "God"     | "Of"  |"Preston"  |"Frascati" |
| :-:       |:-:            | :-:       |:-:    |:-:        |:-:        |
| MongoDb   | 26ms          | 52.2ms    |53ms   |29.2ms     |26.4ms     |
| neo4j     |               |           |       |           |           |

Medians:

| Tests     | "New York"    | "God"     | "Of"  |"Preston"  |"Frascati" |
| :-:       |:-:            | :-:       |:-:    |:-:        |:-:        |
| MongoDb   | 26ms          | 52ms      |52ms   |27ms       |26ms       |
| neo4j     |               |           |       |           |           |

### Query 2: 

Not applicable, for automation, slowness of user moving data from RoboMongo to our API would alow the process down.

`db.Books.find({title:"-UserInput-"}, { cities: 1)`
Given this document, we can then extract the city data and query our City collection for lat and lng
`db.City.find({name:"-CityName-"},{lat:1, lng:1})`
Returns latitude and longitude to be passed on to our connection to the google API for map generation.

### Query 3:
Similarly combines Query 1 and 2 somewhat.
`db.Books.find({author:"-UserInput-"}, { title: 1, cities: 1)`
To send on cities to our City collection and thus get latitude and longitude
`db.City.find({name:"-CityName-"},{lat:1, lng:1})`

### Query 4:
One way of doing it in mongodb 
`db.City.find({lat:{"$gt": ~(Lat-1)~}, lat:{"$lt": ~(Lat+1)~},lng:{"$gt": ~(lng-1)~},lng:{"$lt": ~(Lng+1)~}},{name:1})`
