#include <iostream>
#include <unordered_map>
#include <string>
using namespace std;

class URLShortener {
    unordered_map<string, string> urlMap;
    const string BASE = "http://short.ly/";

public:
    string shorten(const string &longUrl) {
        string shortUrl = BASE + to_string(hash<string>{}(longUrl));
        urlMap[shortUrl] = longUrl;
        return shortUrl;
    }

    string retrieve(const string &shortUrl) {
        return urlMap.count(shortUrl) ? urlMap[shortUrl] : "";
    }
};

int main() {
    URLShortener shortener;
    string url = "https://github.com";
    string s_url = shortener.shorten(url);
    cout << "Short URL: " << s_url << endl;
    cout << "Original URL: " << shortener.retrieve(s_url) << endl;
}
