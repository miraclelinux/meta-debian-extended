# base recipe: meta/recipes-graphics/xorg-lib/libx11_1.6.7.bb
# base branch: warrior
# base commit: 7862ee16890828507f17b0e8e5390e0310776e8a

require libx11.inc

SRC_URI += "file://disable_tests.patch \
            file://0001-Fix-hanging-issue-in-_XReply.patch \
           "
# NOTE: Update Fix-hanging-issue-in-_XReply.patch for debian package.

inherit gettext

do_configure_append () {
    sed -i -e "/X11_CFLAGS/d" ${B}/src/util/Makefile
}

BBCLASSEXTEND = "native nativesdk"
