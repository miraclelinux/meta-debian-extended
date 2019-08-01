# base recipe: meta/recipes-graphics/libepoxy/libepoxy_1.5.3.bb
# base branch: warrior
# base commit: a9f1348af9f26da65e6a01d9575abd6a5f4555b2

SUMMARY = "OpenGL function pointer management library"
HOMEPAGE = "https://github.com/anholt/libepoxy/"
SECTION = "libs"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING;md5=58ef4c80d401e07bd9ee8b6b58cf464b"

UPSTREAM_CHECK_URI = "https://github.com/anholt/libepoxy/releases"

inherit debian-package
require recipes-debian/sources/${BPN}.inc

inherit meson pkgconfig distro_features_check

REQUIRED_DISTRO_FEATURES = "opengl"
REQUIRED_DISTRO_FEATURES_class-native = ""
REQUIRED_DISTRO_FEATURES_class-nativesdk = ""

PACKAGECONFIG[egl] = "-Degl=yes, -Degl=no, virtual/egl"
PACKAGECONFIG[x11] = "-Dglx=yes, -Dglx=no, virtual/libx11 virtual/libgl"
PACKAGECONFIG ??= "${@bb.utils.filter('DISTRO_FEATURES', 'x11', d)} egl"

EXTRA_OEMESON += "-Dtests=false"

PACKAGECONFIG_class-native = "egl"
PACKAGECONFIG_class-nativesdk = "egl"

BBCLASSEXTEND = "native nativesdk"

# This will ensure that dlopen will attempt only GL libraries provided by host
do_install_append_class-native() {
	chrpath --delete ${D}${libdir}/*.so
}

do_install_append_class-nativesdk() {
	chrpath --delete ${D}${libdir}/*.so
}
