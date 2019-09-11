# base recipe: meta-webserver/recipes-support/spawn-fcgi/spawn-fcgi_1.6.4.bb
# base branch: warrior
# base commit: 0c31f55bcfd6630d894dd2dda6ca483bea5de4ab

SUMMARRY = "spawn-fcgi is used to spawn FastCGI applications"
HOMEPAGE = "http://redmine.lighttpd.net/projects/spawn-fcgi"

inherit debian-package
require recipes-debian/sources/spawn-fcgi.inc

LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://COPYING;md5=e4dac5c6ab169aa212feb5028853a579"

SRC_URI += " file://fix_configure_ipv6_test.patch"

inherit autotools

PACKAGECONFIG ??= "${@bb.utils.filter('DISTRO_FEATURES', 'ipv6', d)}"
PACKAGECONFIG[ipv6] = "--enable-ipv6,--disable-ipv6,"
