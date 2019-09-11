# base recipe: meta-openembedded/meta-perl/recipes-perl/libterm/libterm-readkey-perl_2.38.bb 
# base branch: master
# base commit: 2c5208a9b1ac84c09abde03a0ab5b69237dc1e37

SUMMARY = "Term::ReadKey - A perl module for simple terminal control."
DESCRIPTION = "Term::ReadKey is a compiled perl module dedicated to providing simple \
control over terminal driver modes (cbreak, raw, cooked, etc.,) support \
for non-blocking reads, if the architecture allows, and some generalized \
handy functions for working with terminals. One of the main goals is to \
have the functions as portable as possible, so you can just plug in 'use \
Term::ReadKey' on any architecture and have a good likelihood of it \
working."
HOMEPAGE = "http://search.cpan.org/~jstowe/TermReadKey-${PV}"
SECTION = "libraries"

LICENSE = "Artistic-1.0 | GPLv1+"
LIC_FILES_CHKSUM = "file://README;md5=c275db663c8489a5709ebb22b185add5"

inherit debian-package
require recipes-debian/sources/libterm-readkey-perl.inc

DEBIAN_QUILT_PATCHES = ""
DEBIAN_UNPACK_DIR = "${WORKDIR}/TermReadKey-${PV}"

S = "${WORKDIR}/TermReadKey-${PV}"

# It needs depend on native to let dynamic loader use native modules
# rather than target ones.
DEPENDS = "libterm-readkey-perl-native"

inherit cpan ptest-perl

RDEPENDS_${PN}-ptest += " \
    perl-module-test-more \
"

do_configure_append () {
    # Hack the dynamic module loader so that it use native modules since it can't load
    # the target ones.
    if [ "${BUILD_SYS}" != "${HOST_SYS}" ]; then
        sed -i -e "s#-I\$(INST_ARCHLIB)#-I${STAGING_BINDIR_NATIVE}/perl-native/perl/vendor_perl/${@get_perl_version(d)}#g" Makefile
    fi
}

BBCLASSEXTEND = "native"
