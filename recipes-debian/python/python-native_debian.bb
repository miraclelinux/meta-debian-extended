# base recipe: meta/recipes-devtools/python/python-native_2.7.16.bb
# base branch: warrior
# base commit: 2268bf548a2f7e06da9071b11b4c7a487859778d

require python.inc
EXTRANATIVEPATH += "bzip2-native"
DEPENDS = "openssl-native bzip2-replacement-native zlib-native readline-native sqlite3-native expat-native gdbm-native db-native"

inherit debian-package
require recipes-debian/sources/python2.7.inc

LICENSE = "PSFv2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e466242989bd33c1bd2b6a526a742498"

DEBIAN_UNPACK_DIR = "${WORKDIR}/Python-${PV}"
BPN = "python2.7"
DEBIAN_PATCH_TYPE = "quilt"

FILESPATH_append = ":${COREBASE}/meta/recipes-devtools/python/python-native"
FILESPATH_append = ":${COREBASE}/meta/recipes-devtools/python/python"
FILESPATH_append = ":${THISDIR}/python"

SRC_URI += "\
            file://05-enable-ctypes-cross-build.patch \
            file://10-distutils-fix-swig-parameter.patch \
            file://11-distutils-never-modify-shebang-line.patch \
            file://0001-distutils-set-the-prefix-to-be-inside-staging-direct-rebase.patch \
            file://debug.patch \
            file://unixccompiler.patch \
            file://multilib-debian-native.patch \
            file://add-md5module-support.patch \
            file://builddir.patch \
            file://0001-python-native-fix-one-do_populate_sysroot-warning.patch \
            file://bpo-35907-cve-2019-9948-fix.patch \
            file://dont-use-multiarch.patch \
           "

FILESEXTRAPATHS =. "${FILE_DIRNAME}/${PN}:"

inherit native

EXTRA_OECONF_append = " --bindir=${bindir}/${PN} --with-system-expat=${STAGING_DIR_HOST}"

EXTRA_OEMAKE = '\
  LIBC="" \
  STAGING_LIBDIR=${STAGING_LIBDIR_NATIVE} \
  STAGING_INCDIR=${STAGING_INCDIR_NATIVE} \
'

do_debian_patch_prepend() {
    export DEB_TARGET_ARCH=${DPKG_ARCH}
    export DEB_TARGET_ARCH_OS=${TARGET_OS}

    cd ${DEBIAN_UNPACK_DIR}
    # Generage ./debian/patches/series
    make -f ./debian/rules ./debian/patches/series
}

do_configure_append() {
	autoreconf --verbose --install --force --exclude=autopoint ../Python-${PV}/Modules/_ctypes/libffi
}

# Regenerate all of the generated files
# This ensures that pgen and friends get created during the compile phase
do_compile_prepend() {
    oe_runmake regen-all
}

do_install() {
	oe_runmake 'DESTDIR=${D}' install
	install -d ${D}${bindir}/${PN}
	install -m 0755 Parser/pgen ${D}${bindir}/${PN}

	# Make sure we use /usr/bin/env python
	for PYTHSCRIPT in `grep -rIl ${bindir}/${PN}/python ${D}${bindir}/${PN}`; do
		sed -i -e '1s|^#!.*|#!/usr/bin/env python|' $PYTHSCRIPT
	done

	# Add a symlink to the native Python so that scripts can just invoke
	# "nativepython" and get the right one without needing absolute paths
	# (these often end up too long for the #! parser in the kernel as the
	# buffer is 128 bytes long).
	ln -s python-native/python ${D}${bindir}/nativepython

	# We don't want modules in ~/.local being used in preference to those
	# installed in the native sysroot, so disable user site support.
	sed -i -e 's,^\(ENABLE_USER_SITE = \).*,\1False,' ${D}${libdir}/python${PYTHON_MAJMIN}/site.py
}

python(){

    # Read JSON manifest
    import json
    pythondir = d.getVar('THISDIR')
    with open(pythondir+'/python/python2-manifest.json') as manifest_file:
        manifest_str =  manifest_file.read()
        json_start = manifest_str.find('# EOC') + 6
        manifest_file.seek(json_start)
        manifest_str = manifest_file.read()
        python_manifest = json.loads(manifest_str)

    rprovides = d.getVar('RPROVIDES').split()

    # Hardcoded since it cant be python-native-foo, should be python-foo-native
    pn = 'python'

    for key in python_manifest:
        pypackage = pn + '-' + key + '-native'
        if pypackage not in rprovides:
              rprovides.append(pypackage)

    d.setVar('RPROVIDES', ' '.join(rprovides))
}
