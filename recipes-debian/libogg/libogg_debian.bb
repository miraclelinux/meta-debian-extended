# base recipe: meta/recipes-multimedia/libogg/libogg_1.3.3.bb
# base branch: warrior
# base commit: e3a691c834f022882951a5fd5d2e5e28d7dfbc03

SUMMARY = "Ogg bitstream and framing libary"
DESCRIPTION = "libogg is the bitstream and framing library \
for the Ogg project. It provides functions which are \
necessary to codec libraries like libvorbis."
HOMEPAGE = "http://xiph.org/"
BUGTRACKER = "https://trac.xiph.org/newticket"
SECTION = "libs"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://COPYING;md5=db1b7a668b2a6f47b2af88fb008ad555 \
                    file://include/ogg/ogg.h;beginline=1;endline=11;md5=eda812856f13a3b1326eb8f020cc3b0b"

inherit debian-package
require recipes-debian/sources/libogg.inc
DEBIAN_PATCH_TYPE = "nopatch"

inherit autotools pkgconfig
