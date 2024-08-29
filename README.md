# Document Search Engine

## Table of Contents

1. [Overview](#overview)
2. [Features](#features)
    1. [Create](#create)
    2. [Undo](#undo)
    2. [Search](#search)
    3. [Serialization](#serialization)
    4. [Delete](#delete)
3. [Implemented Data Structures](#implemented-data-structures)
    1. [BTree](#btree)
    2. [MinHeap](#minheap)
    3. [Stack](#stack)
    4. [Trie](#trie)

## Overview

This project is a document store that can create, delete, undo changes, and search through documents. The project was made to learn about the intricacies of data structures and how they are best implemented in real applications. 

## Features

### Create

The DocumentStore class can create both text documents and binary data documents. 

### Undo 

The DocumentStore class can undo changes including the creation, deletion and changes to the metadata of documents. These undos can either be done to the last edited document or to the last change to a specific document. This was done using a stack holding lambda expressions.

### Search

Text documents can be searched through either for a keyword, part of a keyword, and/or metadata. This was done by implementing tries to store keywords and metadata.

### Serialization

DocumentStore can automatically serialize and deserialize documents between memory and storage. To do this, it uses the Gson library to turn the documents into custom Json objects and back. The DocumentStore keeps track of which documents were edited the least recently using a heap. If memory goes over the allotted amount it will move documents to disk until it is compliant. If a document in storage needs to be edited, it is automatically brought back into memory and another document will be pushed to storage if necessary.

### Delete 

Deleted documents are removed from all relevant data structures that they are stored in, including a BTree, 2 Tries, and a Heap. Documents can be deleted individually or based off of a keyword, part of a keyword, and/or metadata.

## Implemented Data Structures

### BTree

Implemented a B+Tree to effectively store documents. The tree uses the document's URI as a key. It also supports the movement of documents between memory and storage.

### MinHeap

Implemented a MinHeap to facilitate the tracking of which documents were used the least recently. This was useful for deciding which documents to move to disk.

### Stack

Implemented a Stack to keep track of which edits were done last. This was helpful in the implementation of the undo feature.

### Trie

Implemented a Trie to effectively associate keywords and metadata to documents.