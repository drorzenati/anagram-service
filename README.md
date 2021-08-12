# Anagram Resolving

## Note:
To view the readme in http style view (like in github) use https://markdownlivepreview.com/

## Description:
This service is solving the anagram problem on large scaled data store.
To handle the possible performance issue, during service startup, the data store is fetched to an in-memory "Trie" (prefix tree) in a sorted manner .
The logic for inserting the word into the cache is as such:
1. Sort the word in an alphabetic manner (so 2 word with the same alphabetic sorting value will be mapped to the same node in the tree).
2. iterate over the sorted word character by character (if an edge does not exist, create it) until the last character.
3. when reaching the leaf, insert the original word in to the tree.

For example: for the words "apple","appel" the tree will look as such:
root - a ->
e ->
l ->
p ->
p -> (anagrams: apple, appel)


## APIs
1. GET /api/v1/similar?word=<some-word>
   the service is calling the cache (trie) to get the anagrams.
   the time complexity is for traversing over the tree char be char = O(wordLength) = O(1)

2. GET /api/v1/stats
   the service gathering statistics for the number of calls and average time of processing (only the calls for /api/v1/similar?word=<some-word> api).
   The way to calculate it is using spring actuator filter service. each call to the service is directed to MetricFilter (before calling the API itself). 
   the metric filter gathers the statistics before and after the calls to the service's APIs and stores it locally (the update of the structure is done synchronously)

