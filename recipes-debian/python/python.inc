# base recipe: meta/recipes-devtools/python/python.inc
# base branch: warrior
# base commit: 2268bf548a2f7e06da9071b11b4c7a487859778d

SUMMARY = "The Python Programming Language"
HOMEPAGE = "http://www.python.org"
LICENSE = "PSFv2"
SECTION = "devel/python"
# bump this on every change in contrib/python/generate-manifest-2.7.py
INC_PR = "r0"

LIC_FILES_CHKSUM = "file://LICENSE;md5=e466242989bd33c1bd2b6a526a742498"

CVE_PRODUCT = "python"

PYTHON_MAJMIN = "2.7"

inherit autotools pkgconfig

EXTRA_OECONF = "\
  --with-threads \
  --with-pymalloc \
  --without-cxx-main \
  --with-signal-module \
  --enable-shared \
  --enable-ipv6=${@bb.utils.contains('DISTRO_FEATURES', 'ipv6', 'yes', 'no', d)} \
  ac_cv_header_bluetooth_bluetooth_h=no ac_cv_header_bluetooth_h=no \
  ${PYTHONLSBOPTS} \
"

do_configure_prepend() {
	libdirleaf="$(echo ${libdir} | sed -e 's:${prefix}/::')"
	sed -i -e "s:SEDMELIBLEAF:${libdirleaf}:g" \
		${S}/configure.ac
}

do_install_append () {
	sed -i -e 's:${HOSTTOOLS_DIR}/install:install:g' \
		-e 's:${HOSTTOOLS_DIR}/mkdir:mkdir:g' \
		${D}/${libdir}/python${PYTHON_MAJMIN}/_sysconfigdata.py
}
