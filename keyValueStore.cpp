#include <bits/stdc++.h>
using namespace std;

class KeyValueStore {
    unordered_map<string, string> store;

public:
    void put(string key, string value) {
        store[key] = value;
    }

    string get(string key) {
        return store.count(key) ? store[key] : "NOT_FOUND";
    }

    void remove(string key) {
        store.erase(key);
    }
};

int main() {
    KeyValueStore db;
    db.put("name", "Alice");
    db.put("age", "22");

    cout << "Name: " << db.get("name") << endl;
    cout << "Age: " << db.get("age") << endl;

    db.remove("age");
    cout << "Age after deletion: " << db.get("age") << endl;
}
