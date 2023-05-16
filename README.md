## Overview

**rocksdb-cli** is a CLI that enables simple interactions on an underlying RocksDB

## Features
- Get the value (if it exists) associated to a key
- Create / update a value given the key
- Delete a value given the key

## Requirements

- Java 17+
- Maven

## Build


`mvn clean package`


## Configuration

The RocksDB CLI can be configured using an external **application.properties** file located side by side to the application jar.


## Read / Write (single instance) mode

The CLI can open / create a RocksDB instance in Read / Write mode.<br/>
In this mode the CLI can get, put or delete key / values operating on a single RocksDB instance

#### How to configure the read / write mode

This mode can be enabled using the following properties: <br/>
**rocksdb.folder.path** spevifying the full pathname of the RocksDB folder

In this case the application.properties file can be: <br/><br/>
**application.properties** <br/>
`rocksdb.folder.path=/ny-folder1/my-folder2/rocksdb-folder`

## Read only (multiple instances) mode

In this mode the CLI can open a set of RocksDB folders and perform readonly operations on them.
In this mode it's possible to get the values associated to a key iterating over the underlying RocksDB instances.

#### How to configure the read only mode

This mode can be enabled using the following properties: <br/>
**db.multi.folder.read=true** <br/>
**rocksdb.folder.paths** specifying the list of RocksDB folders using the comma (,) separator

In this case the application.properties file can be: <br/><br/>
**application.properties** <br/>
`
db.multi.folder.read=true
rocksdb.folder.paths=/ny-folder1/my-folder2/rocksdb-folder1,/ny-folder1/my-folder2/rocksdb-folder2,/ny-folder1/my-folder2/rocksdb-folder3
`

## Run
`java -jar rocksdb-cli-1.0.0.jar`

The CLI starts and will waits for the commands to execute

## Available commands

The list of available commands can be printed using the command <br/>
`help`

### Get
Get the value (if it exists) given it's key

`get key`

### Put 
**(not available in read only mode)**

Creates or updates a key / value pair

`put key value`

### Delete 
**(not available in read only mode)**

Deletes a key / value pair given the key

`delete key`