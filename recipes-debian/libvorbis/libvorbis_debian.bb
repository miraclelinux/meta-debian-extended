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
           file://CVE-2018-10392.patch \
           file://CVE-2017-14160.patch \
          "

inherit autotools pkgconfig
