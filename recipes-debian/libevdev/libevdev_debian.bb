# base recipe: meta/recipes-support/libevdev/libevdev_1.6.0.bb
# base branch: warrior
# base commit: 96eba176452624b69b1b1dd91b117a458aeb2c92

SUMMARY = "Wrapper library for evdev devices"
HOMEPAGE = "http://www.freedesktop.org/wiki/Software/libevdev/"
SECTION = "libs"

LICENSE = "MIT-X"
LIC_FILES_CHKSUM = "file://COPYING;md5=75aae0d38feea6fda97ca381cb9132eb \
                    file://libevdev/libevdev.h;endline=21;md5=7ff4f0b5113252c2f1a828e0bbad98d1"

inherit debian-package
require recipes-debian/sources/libevdev.inc
DEBIAN_QUILT_PATCHES = ""

inherit autotools pkgconfig
