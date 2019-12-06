# base recipe: poky/meta/recipes-extended/psmisc/psmisc_23.2.bb
# base branch: warrior
# base commit: 60973c804e562360084050395148d2f88cbadd32

require psmisc.inc
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=0636e73ff0215e8d672dc4c32c317bb3"

FILESPATH_append = ":${COREBASE}/meta/recipes-extended/psmisc/psmisc/"

SRC_URI += " file://0001-Use-UINTPTR_MAX-instead-of-__WORDSIZE.patch \
             file://0001-Makefile.am-create-src-directory-before-attempting-t.patch \
             file://add-arm64-support-to-peekfd.patch
           "
