# Organizational Structural Relationship Specification
We have implemented three different encrypted search indexing methods in `index1`, `index2`, and `index3`. In `index3`, our EVBTree is the most sophisticated implementation that combines features from the other approaches.

Our architecture follows a layered approach where:
1. `general_tools` and `encryption` provide foundational services
2. `ChainAddress` provides shared data structures
3. `index1`, `index2`, and `index3` implement different search strategies with varying levels of complexity and security
4. `test_02` serves as the integration layer demonstrating system functionality


## Functional Implementation Hierarchy

The directories implement three distinct indexing strategies:

**Index1** implements a Bloom filter-based approach that uses fuzzy search capabilities through LSH (Locality Sensitive Hashing) and keyword vectorization.

**Index2** implements a binary tree-based encrypted index  using hash tables with chain addressing and cryptographic functions.

**Index3** contains the EVBTree (Encrypted Verifiable Binary Tree) implementation which combines encryption, verification, and fuzzy search capabilities.

## Dependencies and Cross-Package Relationships

### Shared Foundation Dependencies
All indexing implementations depend on the `general_tools` package for core functionality:
- Index1 imports LSH, TopThreeIndexes, and UniGramKeywordVectorGenerator 
- EVBTree uses the same general tools for fuzzy processing

### Encryption Infrastructure
Both Index2 and EVBTree rely heavily on the `encryption` package:
- Index2 uses hash and hmac components for cryptographic operations 
- EVBTree imports the same encryption utilities

### Cross-Index Dependencies
There are notable cross-dependencies between index implementations:
- Index1 imports Stemmer from index3
- EVBTree imports DataItem and HashTable from index2's ChainAddress package 

### Data Structure Sharing
The `ChainAddress` directory exists in two locations, indicating shared data structure implementations:
- A root-level ChainAddress directory provides basic hash table functionality
- Index2 has its own ChainAddress subdirectory with the same components, used by both Index2 and EVBTree

## Integration and Testing
The `test_02` directory demonstrates how all components work together, importing from all index packages and general tools  to create and test the complete encrypted search system.
