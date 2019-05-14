# poky rev: 753e2a0ede4449917c75353b57f13bbafe70fac8
# file: https://git.yoctoproject.org/cgit/cgit.cgi/poky/tree/meta/recipes-support/lz4/lz4_1.8.3.bb?id=753e2a0ede4449917c75353b57f13bbafe70fac8
# branch: master
SUMMARY = "Extremely Fast Compression algorithm"
DESCRIPTION = "LZ4 is a very fast lossless compression algorithm, providing compression speed at 400 MB/s per core, scalable with multi-cores CPU. It also features an extremely fast decoder, with speed in multiple GB/s per core, typically reaching RAM speed limits on multi-core systems."
SECTION = "libs"

require lz4.inc

