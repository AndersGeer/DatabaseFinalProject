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
Based on this, I provide a machine configuration which allows you to download all the books via a Digitalocean Droplet https://github.com/HelgeCPH/db_course_nosql/tree/master/book_download. 
Alternatively, you can get all the books from a USB key in class.
* A CSV file with many cities and their geolocations is avalable from www.geonames.org http://download.geonames.org/export/dump/ 
where we are especially interested in file http://download.geonames.org/export/dump/cities15000.zip or http://download.geonames.org/export/dump/cities5000.zip
* You write a program that scans each book, i.e., each text file and extracts all city names. 
For this task a heuristic is good enough. That is, it is okay if you miss cities with extravagant names.
* Your databases store thus, author names, book titles, and names of cities, their geolocations and their occurences in texts.
* The frontend of your application does not matter too much. That is, a CLI application is as well as a web-application.
```

