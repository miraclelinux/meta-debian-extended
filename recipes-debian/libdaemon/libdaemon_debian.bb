# base recipe: meta/recipes-support/libdaemon/libdaemon_0.14.bb
# base branch: warrior
# base commit: 933967fc9f10afb2c3cd789ba4ec3510e81cbdab

SUMMARY = "Lightweight C library which eases the writing of UNIX daemons"
SECTION = "libs"
AUTHOR = "Lennart Poettering <lennart@poettering.net>"
HOMEPAGE = "http://0pointer.de/lennart/projects/libdaemon/"
LICENSE = "LGPLv2.1+"
LIC_FILES_CHKSUM = "file://LICENSE;md5=2d5025d4aa3495befef8f17206a5b0a1 \
                    file://libdaemon/daemon.h;beginline=9;endline=21;md5=bd9fbe57cd96d1a5848a8ba12d9a6bf4"

inherit debian-package
require recipes-debian/sources/${BPN}.inc

FILESPATH_append = ":${COREBASE}/meta/recipes-support/${BPN}/${BPN}"
SRC_URI += "file://fix-includes.patch"

inherit autotools pkgconfig

EXTRA_OECONF = "--disable-lynx"
