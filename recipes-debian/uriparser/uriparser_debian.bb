# base recipe: meta-oe/recipes-support/uriparser/uriparser_0.8.4.bb
# base branch: warrior
# base commit: a24acf94d48d635eca668ea34598c6e5c857e3f8

SUMMARY = "RFC 3986 compliant URI parsing library"
HOMEPAGE = "https://uriparser.github.io"

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://COPYING;md5=fc3bbde670fc6e95392a0e23bf57bda0"

inherit debian-package
require recipes-debian/sources/uriparser.inc

DEBIAN_QUILT_PATCHES = ""

inherit autotools

EXTRA_OECONF = "--disable-test --disable-doc"

BBCLASSEXTEND += "native"
