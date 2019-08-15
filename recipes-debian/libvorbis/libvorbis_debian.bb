# base recipe: meta/recipes-multimedia/libvorbis/libvorbis_1.3.6.bb
# base branch: warrior
# base commit: 7204e57262c5dffe3e2c9a2e59c3f2e9e4e35514

SUMMARY = "Ogg Vorbis Audio Codec"
DESCRIPTION = "Ogg Vorbis is a high-quality lossy audio codec \
that is free of intellectual property restrictions. libvorbis \
is the main vorbis codec library."
HOMEPAGE = "http://www.vorbis.com/"
BUGTRACKER = "https://trac.xiph.org"
SECTION = "libs"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://COPYING;md5=70c7063491d2d9f76a098d62ed5134f1 \
                    file://include/vorbis/vorbisenc.h;beginline=1;endline=11;md5=d1c1d138863d6315131193d4046d81cb"

inherit debian-package
require recipes-debian/sources/libvorbis.inc
FILESPATH_append = ":${COREBASE}/meta/recipes-multimedia/libvorbis/libvorbis"

DEPENDS = "libogg"

SRC_URI += " \
           file://0001-configure-Check-for-clang.patch \
          "

# Remove CVE-2018-10392.patch
#   same as debian/patches/0004-Sanity-check-number-of-channels-in-setup.patch
# Remove CVE-2017-14160.patch
#   same as debian/patches/0003-CVE-2017-14160-fix-bounds-check-on-very-low-sample-r.patch

inherit autotools pkgconfig
