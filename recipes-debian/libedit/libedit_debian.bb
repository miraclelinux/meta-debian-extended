# base recipe: meta-openembedded/meta-oe/recipes-devtools/libedit/libedit_20190324-3.1.bb
# base branch: warrior
# base commit: 91b44d79321684ec7b53229e242a294e5a05f807

SUMMARY = "BSD replacement for libreadline"
DESCRIPTION = "Command line editor library providing generic line editing, \
history, and tokenization functions"
HOMEPAGE = "http://www.thrysoee.dk/editline/"
SECTION = "libs"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://COPYING;md5=1e4228d0c5a9093b01aeaaeae6641533"

DEPENDS = "ncurses libbsd"

inherit debian-package
require recipes-debian/sources/libedit.inc
DEBIAN_UNPACK_DIR = "${WORKDIR}/${BPN}-20181209-3.1"

inherit autotools pkgconfig

# upstream site does not allow wget's User-Agent
FETCHCMD_wget += "-U bitbake"
SRC_URI += " \
           file://stdc-predef.patch \
          "

BBCLASSEXTEND = "native nativesdk"
