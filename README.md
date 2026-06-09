# TrainHub Java Labs

5 progressive Java lab exercises for testing TrainHub's lab lifecycle:

| # | Lab | Topic | Difficulty | Duration |
|---|-----|-------|------------|----------|
| 1 | Collections | HashMap, List sorting, filtering | Beginner | 30 min |
| 2 | Streams | Stream API, collectors, grouping | Intermediate | 45 min |
| 3 | Concurrency | Threads, CompletableFuture, sync | Intermediate | 60 min |
| 4 | Design Patterns | Strategy, Observer, Builder | Intermediate | 45 min |
| 5 | REST Client | HttpClient, JSON parsing, error handling | Advanced | 60 min |

## Usage with TrainHub

Import this repo via **Labs > Import from Git** using the GitHub URL. Each `lab-*` subdirectory contains a `.trainhub/lab.yml` — the platform will detect all 5 labs automatically.

Alternatively, import individual labs by pointing to a specific subdirectory.

## Structure

Each lab follows the TrainHub convention:

```
lab-NN-topic/
├── .trainhub/lab.yml    # Lab metadata + grading config
├── pom.xml              # Maven build file
├── README.md            # Instructions (shown to students)
├── starter/             # Files given to students
│   └── src/main/java/
└── tests/               # Auto-grading test files
    └── src/test/java/
```
