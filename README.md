# ByteExtractor

Copy a range of bytes from one file to another.

## Usage

```
Arguments:
    input -> The file to read { String }
    output -> The file write to { String }
Options:
    --start, -s [0] -> The byte to copy from inclusive, if negative the position will be from the end of the file. { Long }
    --end, -e [-1] -> The byte to copy until inclusive, if negative the position will be from the end of the file. { Long }
    --help, -h -> Usage info
```
