# Anagram Resolving

## Description:
This service is solving the anagram problem on large scaled data store.
To handle the possible performance issue, during service startup, the data store is cached as an in-memory "Trie" (prefix tree) in a sorted manner.
The logic for inserting the word into the cache is as such:
1. Sort the word in an alphabetic manner (so 2 words with the same alphabetic sorting value will be mapped to the same node in the tree).
2. Iterate over the sorted word character by character (if an edge does not exist, create it) until the last character.
3. When reaching the last character, insert the original word in to the tree.

For example: for the words "apple","appel" the tree will look as such:
root - a -> e -> l -> p -> p -> (anagrams: apple, appel)

## APIs
1. GET /api/v1/similar?word=<some-word>
   The service is calling the cache (trie) to get the anagrams.
   The time complexity is for traversing over the tree char be char = O(wordLength) = O(1)
   As an additional optimization, there is also spring cache on the method level. i.e, if there are 2 calls with the same word, the second call response is returned immediately (handled by Spring).  

2. GET /api/v1/stats
   The service is gathering statistics for the number of calls and average time of processing the _/api/v1/similar?word=<some-word>_ api.
   The way to calculate it is using spring actuator filter service. each call to the service is directed to MetricFilter (before calling the API itself). 
   The metric filter gathers the statistics before and after the calls to the service's APIs and stores it locally (the update of the structure is done synchronously)

## Note:
To view the readme in http style view (like in github) use https://markdownlivepreview.com/