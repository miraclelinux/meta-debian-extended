# base recipe: meta-openembedde/meta-oe/recipes-extended/rsyslog/libfastjson_0.99.8.bb
# base branch: warrior
# base commit: a74abf155d7bd6c873fd72b76369c09e5c68031c

SUMMARY = "A fork of json-c library"
HOMEPAGE = "https://github.com/rsyslog/libfastjson"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING;md5=a958bb07122368f3e1d9b2efe07d231f"

inherit debian-package
require recipes-debian/sources/libfastjson.inc

DEBIAN_QUILT_PATCHES = ""
DEPENDS = ""

SRC_URI += " file://0001-fix-jump-misses-init-gcc-8-warning.patch"

inherit autotools
