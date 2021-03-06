# Copyright 2017 Google Inc. All Rights Reserved.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

"""service-management list command."""

from __future__ import absolute_import
from __future__ import division
from __future__ import unicode_literals
from apitools.base.py import list_pager

from googlecloudsdk.api_lib.endpoints import services_util
from googlecloudsdk.calliope import base


class List(base.ListCommand):
  """List services for a project.

  This command lists the services that are produced by a project.

  ## EXAMPLES

  To list the services the current project produces, run:

    $ {command}
  """

  _DEFAULT_PAGE_SIZE = 2000

  @staticmethod
  def Args(parser):
    """Args is called by calliope to gather arguments for this command.

    Args:
      parser: An argparse parser that you can use to add arguments that go
          on the command line after this command. Positional arguments are
          allowed.
    """
    # Remove unneeded list-related flags from parser
    base.URI_FLAG.RemoveFromParser(parser)

    parser.display_info.AddFormat("""
          table(
            serviceName:label=NAME,
            serviceConfig.title
          )
        """)

  def Run(self, args):
    """Run 'endpoints list'.

    Args:
      args: argparse.Namespace, The arguments that this command was invoked
          with.

    Returns:
      The list of managed services for this project.
    """
    client = services_util.GetClientInstance()

    validated_project = services_util.GetValidatedProject(args.project)

    request = services_util.GetProducedListRequest(validated_project)

    return list_pager.YieldFromList(
        client.services,
        request,
        limit=args.limit,
        batch_size_attribute='pageSize',
        batch_size=args.page_size or self._DEFAULT_PAGE_SIZE,
        field='services')
